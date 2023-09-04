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
import src.ir.type.*;
import src.utils.Position;
import src.utils.error.IRError;
import src.utils.scope.GlobalScope;

import java.util.HashMap;
import java.util.Objects;

public class InstSelection implements IRVisitor {
    public GlobalScope globalScope;

    ASMBlock curBlock;
    ASMFunction curFunc;


    private final PhysReg sp = PhysReg.regMap.get("sp");
    private final PhysReg ra = PhysReg.regMap.get("ra");
    private final PhysReg fp = PhysReg.regMap.get("s0");
    private final PhysReg a0 = PhysReg.regMap.get("a0");
    private final PhysReg t0 = PhysReg.regMap.get("t0");
    private final PhysReg t1 = PhysReg.regMap.get("t1");
    private final PhysReg t2 = PhysReg.regMap.get("t2");
    private final PhysReg t3 = PhysReg.regMap.get("t3");
    private final PhysReg t4 = PhysReg.regMap.get("t4");

//    public HashMap<Integer,VirtReg> phiMap = new HashMap<>();

    public InstSelection(GlobalScope globalScope) {
        this.globalScope = globalScope;

        // global variables and global string
        for (String key : globalScope.globalVars.keySet()) {
            GlobalVariable v = globalScope.globalVars.get(key);
            Integer init = getConst(v.init);
            v.operand = new GlobalVar(v.name.substring(1), v.type.size, Objects.requireNonNullElse(init, 0));
            globalScope.gVars.add((GlobalVar) v.operand);
        }
        for (String key : globalScope.stringConst.keySet()) {
            StringConst str = globalScope.stringConst.get(key);
            str.operand = new GlobalStr(str.name.substring(1), str.value);
            globalScope.gStrs.add((GlobalStr) str.operand);
        }

        // init functions
        for (String key : globalScope.buildinFunc.keySet()) {
            Function buildinFunc = globalScope.buildinFunc.get(key);
            buildinFunc.operand = new ASMFunction(buildinFunc.name.substring(1));
        }
        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            func.operand = new ASMFunction(func.name.substring(1));
            globalScope.funcs.add((ASMFunction) func.operand);
        }

        for (String key : globalScope.functions.keySet()) {
            globalScope.functions.get(key).accept(this);
        }
    }

    @Override
    public void visit(Function func) {
        curFunc = (ASMFunction) func.operand;

        int paramCnt = 0;
        for (var block : func.blocks) {
            block.operand = new ASMBlock(".L" + block.name);
            curFunc.blocks.add((ASMBlock) block.operand);
            for (var inst : block.insts) {
                if (inst instanceof src.ir.inst.CallInst) {
                    paramCnt = Math.max(paramCnt, ((src.ir.inst.CallInst) inst).paramList.size());
                }
            }
        }
        curFunc.paramSpace = (paramCnt > 8 ? paramCnt - 8 : 0) << 2;

        curFunc.entryBlock = (ASMBlock) func.entryBlock.operand;
        curBlock = curFunc.entryBlock;
//        new MvInst(curFunc.cacheRa, ra, curBlock);

        for (int i = 0; i < func.params.size(); ++i) {
            if (i < 8) {
                setRegSize(PhysReg.regMap.get("a" + i), func.params.get(i));
                storeReg(PhysReg.regMap.get("a" + i), func.params.get(i));
            } else {
                setRegSize(t2, func.params.get(i));
                new LoadInst(func.params.get(i).type.size, t2, fp, new Imm((i - 8) << 2), curBlock);
                storeReg(t2, func.params.get(i));
            }
        }

        for (var block : func.blocks) {
            block.accept(this);
        }
//        if (isValue(curFunc.stackSpace)) {
        curFunc.entryBlock.insts.addFirst(new IBinaryInst("addi", fp, sp, new Imm(curFunc.stackSpace), null));
        curFunc.entryBlock.insts.addFirst(new StoreInst(4, fp, sp, new Imm(curFunc.stackSpace - 8), null));
        curFunc.entryBlock.insts.addFirst(new StoreInst(4, ra, sp, new Imm(curFunc.stackSpace - 4), null));
        curFunc.entryBlock.insts.addFirst(new IBinaryInst("addi", sp, sp, new Imm(-curFunc.stackSpace), null));
//        } else {
//            VirtReg reg = new VirtReg();
//            curFunc.entryBlock.insts.addFirst(new LiInst(reg, new Imm(-curFunc.stackSpace), null));
//            curFunc.entryBlock.insts.addFirst(new RBinaryInst("add", sp, sp, reg, null));
//    }

        curFunc = null;
    }

    @Override
    public void visit(Block block) {
        curBlock = (ASMBlock) block.operand;
        if (block.insts.size() > 2) {
            for (int i = 0; i < block.insts.size() - 2; i++) {
                var inst = block.insts.get(i);
                inst.accept(this);
            }
            var inst1 = block.insts.get(block.insts.size() - 2);
            var inst2 = block.insts.get(block.insts.size() - 1);
            if (inst1 instanceof IcmpInst icmp && inst2 instanceof src.ir.inst.BrInst br && !br.isJump && br.con == icmp) {
                loadReg(t0, icmp.lhs);
                loadReg(t1, icmp.rhs);
                switch (icmp.op) {
                    case ">" -> new src.asm.inst.BrInst("bgt", t0, t1, (ASMBlock) br.trueDest.operand, curBlock);
                    case "<" -> new src.asm.inst.BrInst("blt", t0, t1, (ASMBlock) br.trueDest.operand, curBlock);
                    case ">=" -> new src.asm.inst.BrInst("bge", t0, t1, (ASMBlock) br.trueDest.operand, curBlock);
                    case "<=" -> new src.asm.inst.BrInst("ble", t0, t1, (ASMBlock) br.trueDest.operand, curBlock);
                    case "!=" -> new src.asm.inst.BrInst("bne", t0, t1, (ASMBlock) br.trueDest.operand, curBlock);
                    case "==" -> new src.asm.inst.BrInst("beq", t0, t1, (ASMBlock) br.trueDest.operand, curBlock);
                }
                new JumpInst((ASMBlock) br.falseDest.operand, curBlock);
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
        inst.operand = newVirtReg(inst.type.size);
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
            if (isCommutative && inst.lhs instanceof IntConst) {
                var tmp = inst.lhs;
                inst.lhs = inst.rhs;
                inst.rhs = tmp;
            }
            if (inst.rhs instanceof IntConst v) {
                String op_ = op + "i";
                int value = v.value;
                if (op.equals("sub")) {
                    op_ = "addi";
                    value = -value;
                }
                if (isValue(value)) {
                    loadReg(t1, inst.lhs);
                    new IBinaryInst(op_, t0, t1, new Imm(value), curBlock);
                    storeReg(t0, inst);
                    return;
                }
            }
        }
        loadReg(t1, inst.lhs);
        loadReg(t2, inst.rhs);
        new RBinaryInst(op, t0, t1, t2, curBlock);
        storeReg(t0, inst);
    }

    @Override
    public void visit(BitCastInst inst) {
        loadReg(t0, inst.value);
        storeReg(t0, inst);
    }

    @Override
    public void visit(src.ir.inst.BrInst inst) {
        if (!inst.isJump) {
            loadReg(t0, inst.con);
            new BeqzInst(t0, (ASMBlock) inst.falseDest.operand, curBlock);
        }
        new JumpInst((ASMBlock) inst.trueDest.operand, curBlock);
    }

    @Override
    public void visit(src.ir.inst.CallInst inst) {
        for (int i = 0; i < inst.paramList.size(); ++i) {
            var param = inst.paramList.get(i);
            if (i < 8) {
                setRegSize(PhysReg.regMap.get("a" + i), param);
                loadReg(PhysReg.regMap.get("a" + i), param);
            } else {
                setRegSize(t2, param);
                loadReg(t2, param);
                new StoreInst(param.type.size, t2, sp, new Imm((i - 8) << 2), curBlock);
            }
        }
        new CallInst((ASMFunction) inst.func.operand, curBlock);
        if (!(inst.type instanceof VoidType)) {
            setRegSize(a0, inst);
            storeReg(a0, inst);
        }
    }

    @Override
    public void visit(GetElementPtrInst inst) {
        var ptr = inst.ptr;
        var baseType = ((PtrType) ptr.type).baseType;
        if (baseType instanceof ArrayType) { // string
            var s = (GlobalStr) ptr.operand;
            new LuiInst(t0, new Relocation("hi", s), curBlock);
            new IBinaryInst("addi", t0, t0, new Relocation("lo", s), curBlock);
            storeReg(t0, inst);
        } else if (baseType instanceof ClassType) { // class member
            loadReg(t1, ptr);
            var idx = (IntConst) inst.indexList.get(1);
            new IBinaryInst("addi", t0, t1, new Imm(idx.value << 2), curBlock);
            storeReg(t0, inst);
        } else {
            var idx = inst.indexList.get(0);
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
        loadReg(t1, inst.lhs);
        loadReg(t2, inst.rhs);
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
        if (inst.ptr instanceof Const c) { // global variable and string
            new LuiInst(t1, new Relocation("hi", (Global) c.operand), curBlock);
            new LoadInst(((PtrType) inst.ptr.type).baseType.size, t0, t1, new Relocation("lo", (Global) c.operand), curBlock);
        } else {
//            if (inst.ptr.operand instanceof Imm i) {
//                int value = i.value;
//                new LiInst(t0, new Imm(value), curBlock);
//            } else {
                loadReg(t1, inst.ptr);
                new LoadInst(((PtrType) inst.ptr.type).baseType.size, t0, t1, new Imm(0), curBlock);
//            }
        }
        storeReg(t0, inst);
    }

    @Override
    public void visit(PhiInst inst) {
        setRegSize(a0, inst);
    }

    @Override
    public void visit(src.ir.inst.RetInst inst) {
        if (inst.value != null) {
            setRegSize(PhysReg.regMap.get("a0"), inst.value);
            loadReg(PhysReg.regMap.get("a0"), inst.value);
        }
//        new MvInst(ra, curFunc.cacheRa, curBlock);

        curFunc.stackSpace = curFunc.paramSpace + curFunc.allocaSpace;
        curFunc.stackSpace = ((curFunc.stackSpace + 15) >> 4) << 4;

//        if (isValue(curFunc.stackSpace)) {
//            new IBinaryInst("addi", sp, sp, new Imm(curFunc.stackSpace), curBlock);
//        } else {
//            VirtReg reg = new VirtReg();
//            new LiInst(reg, new Imm(curFunc.stackSpace), curBlock);
//            new RBinaryInst("add", sp, sp, reg, curBlock);
//        }
        new LoadInst(4, ra, sp, new Imm(curFunc.stackSpace - 4), curBlock);
        new LoadInst(4, fp, sp, new Imm(curFunc.stackSpace - 8), curBlock);
        new IBinaryInst("addi", sp, sp, new Imm(curFunc.stackSpace), curBlock);
        new RetInst(curBlock);
    }

    @Override
    public void visit(src.ir.inst.StoreInst inst) {
        if (inst.ptr instanceof Const c) { // global variable and string
            loadReg(t0, inst.value);
            new LuiInst(t1, new Relocation("hi", (Global) c.operand), curBlock);
            new StoreInst(inst.value.type.size, t0, t1, new Relocation("lo", (Global) c.operand), curBlock);
        } else {
//            if (inst.value.operand instanceof Imm i) {
//                int value = i.value;
//                new LiInst(t0, new Imm(value), curBlock);
//                storeReg(t0,inst.ptr);
//            } else {
                loadReg(t1, inst.value);
                new MvInst(t0, t1, curBlock);
                storeReg(t0,inst.ptr);
//            }
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
        if (entity instanceof BoolConst || (entity.type instanceof IntType i && i.bitLen == 1)) reg.size = 1;
        else reg.size = 4;
    }

    private VirtReg newVirtReg(int size) {
        if (size == 1) {
            ++curFunc.allocaSpace;
            return new VirtReg(1, curFunc.allocaSpace);
        } else {
            curFunc.allocaSpace += 4;
            return new VirtReg(4, curFunc.allocaSpace);
        }
    }

//    private PhysReg getPhysReg(Entity e, PhysReg tmp) {
//        Reg reg = getReg(e);
//        if (reg instanceof PhysReg p) {
//            return p;
//        } else {
//            loadReg(tmp, e);
//            return tmp;
//        }
//    }

//    private Reg getReg(Entity e) {
//        if (e.operand != null) return (Reg) e.operand;
//        var constVal = getConst(e);
//        if (constVal != null) {
//            if (constVal == 0) {
//                e.operand = PhysReg.regMap.get("zero");
//            } else {
//                VirtReg reg = newVirtReg(e.type.size);
//                new LiInst(reg, new Imm(constVal), curBlock);
//                return reg;
//            }
//        } else {
//            e.operand = newVirtReg(e.type.size);
//        }
//        return (Reg) e.operand;
//    }

    private void loadReg(PhysReg rd, Entity entity) {
        Integer val = getConst(entity);
        if (val != null) new LiInst(rd, new Imm(val), curBlock);
        else {
            if (entity.operand == null) {
                entity.operand = newVirtReg(entity.type.size);
                loadVirtReg(rd, (VirtReg) entity.operand);
            } else if (entity.operand instanceof Global g) {
                new LuiInst(t1, new Relocation("hi", g), curBlock);
                new IBinaryInst("addi", t0, t1, new Relocation("lo", g), curBlock);
                new LoadInst(((PtrType)entity.type).baseType.size,rd,t0,new Imm(0),curBlock);
            } else {
                loadVirtReg(rd, (VirtReg) entity.operand);
            }
        }
    }

    private void loadVirtReg(PhysReg rd, VirtReg reg) {
        Pair<Reg, Imm> pair = getAddr(reg);
        rd.size = reg.size;
        new LoadInst(reg.size, rd, pair.a, pair.b, curBlock);
    }

    private void storeReg(PhysReg rs, Entity entity) {
        Integer val = getConst(entity);
        if (val != null) throw new IRError(new Position(0, 0), "Assign a value to a constant");
        else {
            if (entity.operand == null) {
                entity.operand = newVirtReg(entity.type.size);
                storeVirtReg(rs, (VirtReg) entity.operand);
            } else if (entity.operand instanceof Global g) {
                new LuiInst(t1, new Relocation("hi", g), curBlock);
                new IBinaryInst("addi", t0, t1, new Relocation("lo", g), curBlock);
                new StoreInst(rs.size, rs, t0, new Imm(0), curBlock);
            } else {
                storeVirtReg(rs, (VirtReg) entity.operand);
            }
        }
    }

    private void storeVirtReg(PhysReg tmp, VirtReg reg) {
        Pair<Reg, Imm> pair = getAddr(reg);
        new StoreInst(reg.size, tmp, pair.a, pair.b, curBlock);
    }

    private Pair<Reg, Imm> getAddr(VirtReg reg) {
        if (isValue(reg.offset)) {
            return new Pair<>(fp, new Imm(-reg.offset));
        }
        t3.size = t4.size = 4;
        new LiInst(t4, new Imm(reg.offset), curBlock);
        new RBinaryInst("sub", t3, fp, t4, curBlock);
        return new Pair<>(t3, new Imm(0));
    }
}
