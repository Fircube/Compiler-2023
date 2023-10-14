package src.backend;

import org.antlr.v4.runtime.misc.Pair;
import src.asm.ASMBlock;
import src.asm.ASMFunction;
import src.asm.inst.*;
import src.asm.inst.CallInst;
import src.asm.inst.LoadInst;
import src.asm.inst.RetInst;
import src.asm.inst.StoreInst;
import src.asm.operand.*;
import src.ir.Block;
import src.ir.Entity;
import src.ir.Function;
import src.ir.IRVisitor;
import src.ir.constant.*;
import src.ir.inst.*;
import src.ir.inst.MoveInst;
import src.ir.type.*;
import src.utils.Position;
import src.utils.error.IRError;
import src.utils.scope.GlobalScope;

import java.util.Objects;

public class IntegrBackend implements IRVisitor {
    public GlobalScope globalScope;

    ASMBlock curBlock;
    ASMFunction curFunc;


    private final PhysReg sp = PhysReg.regs.get("sp");
    private final PhysReg ra = PhysReg.regs.get("ra");
    private final PhysReg fp = PhysReg.regs.get("s0");
    private final PhysReg a0 = PhysReg.regs.get("a0");
    private final PhysReg t0 = PhysReg.regs.get("t0");
    private final PhysReg t1 = PhysReg.regs.get("t1");
    private final PhysReg t2 = PhysReg.regs.get("t2");
    private final PhysReg t3 = PhysReg.regs.get("t3");
    private final PhysReg t4 = PhysReg.regs.get("t4");

    public IntegrBackend(GlobalScope globalScope) {
        this.globalScope = globalScope;

        // global variables and global string
        for (String key : globalScope.globalVars.keySet()) {
            GlobalVariable v = globalScope.globalVars.get(key);
            Integer init = getConst(v.init);
            v.ASMoperand = new GlobalVar(v.name.substring(1), v.type.size, Objects.requireNonNullElse(init, 0));
            globalScope.gVars.add((GlobalVar) v.ASMoperand);
        }
        for (String key : globalScope.stringConst.keySet()) {
            StringConst str = globalScope.stringConst.get(key);
            str.ASMoperand = new GlobalStr(str.name.substring(1), str.value);
            globalScope.gStrs.add((GlobalStr) str.ASMoperand);
        }

        // init functions
        for (String key : globalScope.builtinFunc.keySet()) {
            Function buildinFunc = globalScope.builtinFunc.get(key);
            buildinFunc.ASMoperand = new ASMFunction(buildinFunc.name.substring(1));
        }
        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            func.ASMoperand = new ASMFunction(func.name.substring(1));
            globalScope.funcs.add((ASMFunction) func.ASMoperand);
        }

        for (String key : globalScope.functions.keySet()) {
            globalScope.functions.get(key).accept(this);
        }
    }

    @Override
    public void visit(Function func) {
        curFunc = (ASMFunction) func.ASMoperand;

        int paramCnt = 0;
        for (var block : func.blocks) {
            block.ASMoperand = new ASMBlock(".L" + block.name);
            curFunc.blocks.add((ASMBlock) block.ASMoperand);
            for (var inst : block.insts) {
                if (inst instanceof src.ir.inst.CallInst) {
                    paramCnt = Math.max(paramCnt, inst.operands.size() - 1);
                }
            }
        }
//        curFunc.paramSpace = (paramCnt > 8 ? paramCnt - 8 : 0) << 2;

        curFunc.entryBlock = (ASMBlock) func.entryBlock.ASMoperand;
        curFunc.exitBlock = (ASMBlock) func.exitBlock.ASMoperand;
        curBlock = curFunc.entryBlock;

        for (int i = 0; i < func.operands.size(); ++i) {
            if (i < 8) {
                setRegSize(PhysReg.regs.get("a" + i), func.operands.get(i));
                storeReg(PhysReg.regs.get("a" + i), func.operands.get(i));
            } else {
                setRegSize(t2, func.operands.get(i));
                new LoadInst(func.operands.get(i).type.size, t2, fp, new Imm((i - 8) << 2), curBlock);
                storeReg(t2, func.operands.get(i));
            }
        }

        for (var block : func.blocks) {
            block.accept(this);
        }
        for (var block : func.blocks) {
            curBlock = (ASMBlock) block.ASMoperand;
            for (int i = 0; i < block.phiInsts.size(); i++) {
                var inst = block.phiInsts.get(i);
                inst.accept(this);
            }
        }
        curBlock = null;
////        curFunc.stackSpace = curFunc.paramSpace + curFunc.allocaSpace;
//        curFunc.stackSpace = ((curFunc.stackSpace + 15) >> 4) << 4;

//        curFunc.entryBlock.insts.addFirst(new IBinaryInst("addi", fp, sp, new Imm(curFunc.stackSpace), null));
//        curFunc.entryBlock.insts.addFirst(new StoreInst(4, fp, sp, new Imm(curFunc.stackSpace - 8), null));
//        curFunc.entryBlock.insts.addFirst(new StoreInst(4, ra, sp, new Imm(curFunc.stackSpace - 4), null));
//        curFunc.entryBlock.insts.addFirst(new IBinaryInst("addi", sp, sp, new Imm(-curFunc.stackSpace), null));

        for (var block : func.blocks) {
            for (var inst : block.insts) {
                if (inst instanceof src.ir.inst.RetInst) {
//                    new LoadInst(4, ra, sp, new Imm(curFunc.stackSpace - 4), (ASMBlock) block.ASMoperand);
//                    new LoadInst(4, fp, sp, new Imm(curFunc.stackSpace - 8), (ASMBlock) block.ASMoperand);
//                    new IBinaryInst("addi", sp, sp, new Imm(curFunc.stackSpace), (ASMBlock) block.ASMoperand);
                    new RetInst((ASMBlock) block.ASMoperand);
                }
            }
        }
        curFunc = null;
    }

    @Override
    public void visit(Block block) {
        curBlock = (ASMBlock) block.ASMoperand;
        if (block.insts.size() > 2) {
            for (int i = 0; i < block.insts.size() - 2; i++) {
                var inst = block.insts.get(i);
                inst.accept(this);
            }
            var inst1 = block.insts.get(block.insts.size() - 2);
            var inst2 = block.insts.get(block.insts.size() - 1);
            if (inst1 instanceof IcmpInst icmp && inst2 instanceof src.ir.inst.BrInst br && !br.isJump && br.con() == icmp) {
                loadReg(t0, icmp.lhs());
                loadReg(t1, icmp.rhs());
                switch (icmp.op) {
                    case ">" -> new src.asm.inst.BrInst("bgt", t0, t1, (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case "<" -> new src.asm.inst.BrInst("blt", t0, t1, (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case ">=" -> new src.asm.inst.BrInst("bge", t0, t1, (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case "<=" -> new src.asm.inst.BrInst("ble", t0, t1, (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case "!=" -> new src.asm.inst.BrInst("bne", t0, t1, (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case "==" -> new src.asm.inst.BrInst("beq", t0, t1, (ASMBlock) br.trueDest().ASMoperand, curBlock);
                }
                new JumpInst((ASMBlock) br.falseDest().ASMoperand, curBlock);
            } else {
                inst1.accept(this);
                inst2.accept(this);
            }
        } else {
            for (int i = 0; i < block.insts.size(); i++) {
                var inst = block.insts.get(i);
                inst.accept(this);
            }
        }
        curBlock = null;
    }

    @Override
    public void visit(AllocaInst inst) {
////        inst.ASMoperand = newVirtReg(inst.baseType.size);
    }

    @Override
    public void visit(BinaryInst inst) {
        String op = null;
        boolean hasImm = false, isCommutative = false;
        switch (inst.op) {
            case "+" -> {
                op = "add";
                hasImm = true;
                isCommutative = true;
            }
            case "-" -> {
                op = "sub";
                hasImm = true;
            }
            case "*" -> {
                op = "mul";
                isCommutative = true;
            }
            case "/" -> op = "div";
            case "%" -> op = "rem";
            case ">>" -> {
                op = "sra";
                hasImm = true;
            }
            case "<<" -> {
                op = "sll";
                hasImm = true;
            }
            case "&" -> {
                op = "and";
                hasImm = true;
                isCommutative = true;
            }
            case "|" -> {
                op = "or";
                hasImm = true;
                isCommutative = true;
            }
            case "^" -> {
                op = "xor";
                hasImm = true;
                isCommutative = true;
            }
        }
        if (hasImm) {
            if (isCommutative && inst.lhs() instanceof IntConst) {
                var lhs = inst.lhs();
                var rhs = inst.rhs();
                inst.operands.clear();
                inst.operands.add(lhs);
                inst.operands.add(rhs);
            }
            if (inst.rhs() instanceof IntConst v) {
                String op_ = op + "i";
                int value = v.value;
                if (op.equals("sub")) {
                    op_ = "addi";
                    value = -value;
                }
                if (isValue(value)) {
                    loadReg(t1, inst.lhs());
                    new IBinaryInst(op_, t0, t1, new Imm(value), curBlock);
                    storeReg(t0, inst);
                    return;
                }
            }
        }
        loadReg(t1, inst.lhs());
        loadReg(t2, inst.rhs());
        new RBinaryInst(op, t0, t1, t2, curBlock);
        storeReg(t0, inst);
    }

    @Override
    public void visit(BitCastInst inst) {
        loadReg(t0, inst.value());
        storeReg(t0, inst);
    }

    @Override
    public void visit(src.ir.inst.BrInst inst) {
        if (!inst.isJump) {
            loadReg(t0, inst.con());
            new BeqzInst(t0, (ASMBlock) inst.falseDest().ASMoperand, curBlock);
            new JumpInst((ASMBlock) inst.trueDest().ASMoperand, curBlock);
        } else {
            new JumpInst((ASMBlock) inst.dest().ASMoperand, curBlock);
        }
    }

    @Override
    public void visit(src.ir.inst.CallInst inst) {
        for (int i = 0; i < inst.operands.size() - 1; ++i) {
            var param = inst.operands.get(i + 1);
            if (i < 8) {
                setRegSize(PhysReg.regs.get("a" + i), param);
                loadReg(PhysReg.regs.get("a" + i), param);
            } else {
                setRegSize(t2, param);
                loadReg(t2, param);
                new StoreInst(param.type.size, t2, sp, new Imm((i - 8) << 2), curBlock);
            }
        }
        new CallInst((ASMFunction) inst.func().ASMoperand, curBlock);
        if (!(inst.type instanceof VoidType)) {
            setRegSize(a0, inst);
            storeReg(a0, inst);
        }
    }

    @Override
    public void visit(GetElementPtrInst inst) {
        var ptr = inst.ptr();
        var baseType = ((PtrType) ptr.type).baseType;
        if (baseType instanceof ArrayType) { // string
            var s = (GlobalStr) ptr.ASMoperand;
            new LuiInst(t0, new Relocation("hi", s), curBlock);
            new IBinaryInst("addi", t0, t0, new Relocation("lo", s), curBlock);
            storeReg(t0, inst);
        } else if (baseType instanceof ClassType) { // class member
            loadReg(t1, ptr);
            var idx = (IntConst) inst.operands.get(2);
            new IBinaryInst("addi", t0, t1, new Imm(idx.value << 2), curBlock);
            storeReg(t0, inst);
        } else {
            var idx = inst.operands.get(1);
            if (idx instanceof IntConst i) {
                int val = i.value * baseType.size;
                if (isValue(val)) {
                    loadReg(t1, ptr);
                    new IBinaryInst("addi", t0, t1, new Imm(val), curBlock);
                    storeReg(t0, inst);
                } else {
                    loadReg(t1, ptr);
                    new LiInst(t2, new Imm(val), curBlock);
                    new RBinaryInst("add", t0, t1, t2, curBlock);
                    storeReg(t0, inst);
                }
                return;
            }
            if (baseType.size < 4) {
                loadReg(t2, idx);
            } else {
                loadReg(t3, idx);
                new IBinaryInst("slli", t2, t3, new Imm(2), curBlock);
            }
            loadReg(t1, ptr);
            new RBinaryInst("add", t0, t1, t2, curBlock);
            storeReg(t0, inst);
        }
    }

    @Override
    public void visit(IcmpInst inst) {
        loadReg(t1, inst.lhs());
        loadReg(t2, inst.rhs());
        switch (inst.op) {
            case ">" -> new RBinaryInst("sgt", t0, t1, t2, curBlock);
            case "<" -> new RBinaryInst("slt", t0, t1, t2, curBlock);
            case ">=" -> {
                new RBinaryInst("slt", t3, t1, t2, curBlock);
                new IBinaryInst("xori", t0, t3, new Imm(1), curBlock);
            }
            case "<=" -> {
                new RBinaryInst("sgt", t3, t1, t2, curBlock);
                new IBinaryInst("xori", t0, t3, new Imm(1), curBlock);
            }
            case "!=" -> {
                new RBinaryInst("sub", t3, t1, t2, curBlock);
                new IBinaryInst("snez", t0, t3, curBlock);
            }
            case "==" -> {
                new RBinaryInst("sub", t3, t1, t2, curBlock);
                new IBinaryInst("seqz", t0, t3, curBlock);
            }
        }
        storeReg(t0, inst);
    }

    @Override
    public void visit(src.ir.inst.LoadInst inst) {
        if (inst.ptr() instanceof Const c) { // global variable and string
            new LuiInst(t1, new Relocation("hi", (Global) c.ASMoperand), curBlock);
            new LoadInst(((PtrType) inst.ptr().type).baseType.size, t0, t1, new Relocation("lo", (Global) c.ASMoperand), curBlock);
            storeReg(t0, inst);
        } else {
////            t1.size = 4;
            Pair<Reg, Imm> pair = getPtrAddr(t1, inst.ptr());
            setRegSize(t0, inst);
////            new LoadInst(t0.size, t0, pair.a, pair.b, curBlock);
            storeReg(t0, inst);
        }
    }

    @Override
    public void visit(MoveInst inst) {
        setRegSize(t0, inst.src());
        loadReg(t0, inst.src());
        storeReg(t0, inst.dest());
    }

    @Override
    public void visit(PhiInst inst) {
    }

    @Override
    public void visit(src.ir.inst.RetInst inst) {
        if (!inst.operands.isEmpty()) {
            setRegSize(PhysReg.regs.get("a0"), inst.value());
            loadReg(PhysReg.regs.get("a0"), inst.value());
        }
    }

    @Override
    public void visit(src.ir.inst.StoreInst inst) {
        if (inst.ptr() instanceof Const c) { // global variable and string
            loadReg(t0, inst.value());
            new LuiInst(t1, new Relocation("hi", (Global) c.ASMoperand), curBlock);
            new StoreInst(inst.value().type.size, t0, t1, new Relocation("lo", (Global) c.ASMoperand), curBlock);
        } else {
////            t1.size = 4;
            Pair<Reg, Imm> pair = getPtrAddr(t1, inst.ptr());
            setRegSize(t0, inst.value());
            loadReg(t0, inst.value());
            new StoreInst(inst.value().type.size, t0, pair.a, pair.b, curBlock);
        }
    }

    public boolean isValue(int value) {
        return value < (1 << 11) && value >= -(1 << 11);
    }

    public Integer getConst(Entity e) {
        if (e instanceof IntConst) return ((IntConst) e).value;
        else if (e instanceof NullConst) return 0;
        return null;
    }

    private void setRegSize(PhysReg reg, Entity entity) {
////        if (entity instanceof BoolConst || (entity.type instanceof IntType i && i.bitLen == 1)) reg.size = 1;
////        else reg.size = 4;
    }

////    private VirtReg newVirtReg(int size) {
////        if (size == 1) {
////            ++curFunc.allocaSpace;
////            return new VirtReg(1, curFunc.allocaSpace);
////        } else {
////            curFunc.allocaSpace += 4;
////            return new VirtReg(curFunc.allocaSpace);
////        }
////    }

    private void loadReg(PhysReg rd, Entity entity) {
        Integer val = getConst(entity);
        if (val != null) new LiInst(rd, new Imm(val), curBlock);
        else {
            if (entity.ASMoperand == null) {
////                entity.ASMoperand = newVirtReg(entity.type.size);
                loadVirtReg(rd, (VirtReg) entity.ASMoperand);
            } else if (entity.ASMoperand instanceof Global g) {
                new LuiInst(t1, new Relocation("hi", g), curBlock);
                new IBinaryInst("addi", t0, t1, new Relocation("lo", g), curBlock);
                new LoadInst(((PtrType) entity.type).baseType.size, rd, t0, new Imm(0), curBlock);
            } else {
                loadVirtReg(rd, (VirtReg) entity.ASMoperand);
            }
        }
    }

    private void loadVirtReg(PhysReg rd, VirtReg reg) {
        Pair<Reg, Imm> pair = getAddr(reg);
////        rd.size = reg.size;
        new LoadInst(reg.size, rd, pair.a, pair.b, curBlock);
    }

    private void storeReg(PhysReg rs, Entity entity) {
        Integer val = getConst(entity);
        if (val != null) throw new IRError(new Position(0, 0), "Assign a value to a constant");
        else {
            if (entity.ASMoperand == null) {
////                entity.ASMoperand = newVirtReg(entity.type.size);
                storeVirtReg(rs, (VirtReg) entity.ASMoperand);
            } else if (entity.ASMoperand instanceof Global g) {
                new LuiInst(t1, new Relocation("hi", g), curBlock);
                new IBinaryInst("addi", t0, t1, new Relocation("lo", g), curBlock);
////                new StoreInst(rs.size, rs, t0, new Imm(0), curBlock);
            } else {
                storeVirtReg(rs, (VirtReg) entity.ASMoperand);
            }
        }
    }

    private void storeVirtReg(PhysReg tmp, VirtReg reg) {
        Pair<Reg, Imm> pair = getAddr(reg);
        new StoreInst(reg.size, tmp, pair.a, pair.b, curBlock);
    }

    private Pair<Reg, Imm> getAddr(VirtReg reg) {
////        if (isValue(reg.offset)) {
////            return new Pair<>(fp, new Imm(-reg.offset));
////        }
////        t3.size = t4.size = 4;
////        new LiInst(t4, new Imm(reg.offset), curBlock);
////        new RBinaryInst("sub", t3, fp, t4, curBlock);
        return new Pair<>(t3, new Imm(0));
    }

    private Pair<Reg, Imm> getPtrAddr(PhysReg reg, Entity entity) {
////        reg.size = 4;
        if (entity.ASMoperand instanceof Global g) {
            new LuiInst(t1, new Relocation("hi", g), curBlock);
            new IBinaryInst("addi", reg, t1, new Relocation("lo", g), curBlock);
            return new Pair<>(reg, new Imm(0));
        }
        if (entity instanceof AllocaInst a) {
////            if (a.ASMoperand == null) a.ASMoperand = newVirtReg(((PtrType) a.baseType).baseType.size);
            return getAddr((VirtReg) a.ASMoperand);
        }
        loadReg(reg, entity);
        return new Pair<>(reg, new Imm(0));
    }
}
