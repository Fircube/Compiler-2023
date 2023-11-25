package src.backend;

import src.asm.ASMBlock;
import src.asm.ASMFunction;
import src.asm.inst.*;
import src.asm.inst.CallInst;
import src.asm.inst.LoadInst;
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
import src.utils.scope.GlobalScope;

import java.util.ArrayList;
import java.util.Objects;

public class InstSelection implements IRVisitor {
    public GlobalScope globalScope;

    ASMBlock curBlock;
    ASMFunction curFunc;


    private final PhysReg sp = PhysReg.regs.get("sp");
    private final PhysReg ra = PhysReg.regs.get("ra");
    private final PhysReg fp = PhysReg.regs.get("s0");
    private final PhysReg a0 = PhysReg.regs.get("a0");
    private final PhysReg zero = PhysReg.regs.get("zero");

    public InstSelection(GlobalScope globalScope) {
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

            for (var param : func.operands) {
                param.ASMoperand = new VirtReg();
                ((ASMFunction) func.ASMoperand).params.add((Reg) param.ASMoperand);
            }
            globalScope.funcs.add((ASMFunction) func.ASMoperand);
        }

        for (String key : globalScope.functions.keySet()) {
            globalScope.functions.get(key).accept(this);
        }
    }

    @Override
    public void visit(Function func) {
        curFunc = (ASMFunction) func.ASMoperand;

        for (var block : func.blocks) {
            block.ASMoperand = new ASMBlock(".L" + block.name);
            curFunc.blocks.add((ASMBlock) block.ASMoperand);
        }
        for (var block : func.blocks) builtCFG(block);

        curFunc.entryBlock = (ASMBlock) func.entryBlock.ASMoperand;
        curFunc.exitBlock = (ASMBlock) func.exitBlock.ASMoperand;

        curBlock = curFunc.entryBlock;
        new IBinaryInst("addi", sp, sp, new Imm(0, true, Imm.Type.decSp), curBlock);

        // current ra
        VirtReg curRa = new VirtReg();
        new StoreInst(4, ra, sp, new Imm(0), curBlock);
        // save callee
        var callee = new ArrayList<Reg>();
        for (var reg : PhysReg.calleeSaved) {
            var rd = new VirtReg();
            callee.add(rd);
            new MvInst(rd, reg, curBlock);
        }

        for (int i = 0; i < func.operands.size(); ++i) {
            if (i < 8) {
                new MvInst(curFunc.params.get(i), PhysReg.regs.get("a" + i), curBlock);
            } else {
                new LoadInst(4, curFunc.params.get(i), sp, new Imm(i - 8, true, Imm.Type.getArg), curBlock);
            }
        }

        for (var block : func.blocks) {
            block.accept(this);
        }

        // restore
        curBlock = curFunc.exitBlock;
        int i = 0;
        for (var reg : PhysReg.calleeSaved) {
            new MvInst(reg, callee.get(i), curBlock);
            i++;
        }
//        new MvInst(ra, curRa, curBlock);
        new LoadInst(4, ra, sp, new Imm(0), curBlock);

        new IBinaryInst("addi", sp, sp, new Imm(0, true, Imm.Type.incSp), curBlock);
        new src.asm.inst.RetInst(curBlock);
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
                switch (icmp.op) {
                    case ">" ->
                            new src.asm.inst.BrInst("bgt", getReg(icmp.lhs()), getReg(icmp.rhs()), (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case "<" ->
                            new src.asm.inst.BrInst("blt", getReg(icmp.lhs()), getReg(icmp.rhs()), (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case ">=" ->
                            new src.asm.inst.BrInst("bge", getReg(icmp.lhs()), getReg(icmp.rhs()), (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case "<=" ->
                            new src.asm.inst.BrInst("ble", getReg(icmp.lhs()), getReg(icmp.rhs()), (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case "!=" ->
                            new src.asm.inst.BrInst("bne", getReg(icmp.lhs()), getReg(icmp.rhs()), (ASMBlock) br.trueDest().ASMoperand, curBlock);
                    case "==" ->
                            new src.asm.inst.BrInst("beq", getReg(icmp.lhs()), getReg(icmp.rhs()), (ASMBlock) br.trueDest().ASMoperand, curBlock);
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
        inst.ASMoperand = new Imm(curFunc.allocaCnt, true, Imm.Type.alloca);
        ++curFunc.allocaCnt;
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
                inst.operands.add(rhs);
                inst.operands.add(lhs);
            }
            if (inst.rhs() instanceof IntConst v) {
                String op_ = op + "i";
                int value = v.value;
                if (op.equals("sub")) {
                    op_ = "addi";
                    value = -value;
                }
                if (isImm(value)) {
                    new IBinaryInst(op_, getReg(inst), getReg(inst.lhs()), new Imm(value), curBlock);
                    return;
                }
            }
        }
        new RBinaryInst(op, getReg(inst), getReg(inst.lhs()), getReg(inst.rhs()), curBlock);
    }

    @Override
    public void visit(BitCastInst inst) {
        getVal(getReg(inst), inst.value());
    }

    @Override
    public void visit(src.ir.inst.BrInst inst) {
        if (!inst.isJump) {
            new BeqzInst(getReg(inst.con()), (ASMBlock) inst.falseDest().ASMoperand, curBlock);
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
                getVal(PhysReg.regs.get("a" + i), param);
            } else {
                curFunc.paramCnt = Math.max(curFunc.paramCnt, (i - 7));
                new StoreInst(param.type.size, getReg(param), sp, new Imm(i - 8, true, Imm.Type.putArg), curBlock);
            }
        }
        new CallInst((ASMFunction) inst.func().ASMoperand, curBlock);
        if (!(inst.type instanceof VoidType)) {
            new MvInst(getReg(inst), a0, curBlock);
        }
    }

    @Override
    public void visit(GetElementPtrInst inst) {
        var ptr = inst.ptr();
        var baseType = ((PtrType) ptr.type).baseType;
        if (baseType instanceof ArrayType) { // string
            var s = (GlobalStr) ptr.ASMoperand;
            Reg reg = getReg(inst);
            new LuiInst(reg, new Relocation("hi", s), curBlock);
            new IBinaryInst("addi", reg, reg, new Relocation("lo", s), curBlock);
        } else if (baseType instanceof ClassType) { // class member
            var idx = (IntConst) inst.operands.get(2);
            new IBinaryInst("addi", getReg(inst), getReg(ptr), new Imm(idx.value << 2), curBlock);
        } else {
            var idx = inst.operands.get(1);
            if (idx instanceof IntConst i) {
                int val = i.value * baseType.size;
                if (isImm(val)) {
                    new IBinaryInst("addi", getReg(inst), getReg(ptr), new Imm(val), curBlock);
                } else {
                    var reg = new VirtReg();
                    new LiInst(reg, new Imm(val), curBlock);
                    new RBinaryInst("add", getReg(inst), getReg(ptr), reg, curBlock);
                }
                return;
            }
            Reg reg;
            if (baseType.size < 4) {
                reg = getReg(idx);
            } else {
                reg = new VirtReg();
                new IBinaryInst("slli", reg, getReg(idx), new Imm(2), curBlock);
            }
            new RBinaryInst("add", getReg(inst), getReg(ptr), reg, curBlock);
        }
    }

    @Override
    public void visit(IcmpInst inst) {
        switch (inst.op) {
            case ">" -> new RBinaryInst("sgt", getReg(inst), getReg(inst.lhs()), getReg(inst.rhs()), curBlock);
            case "<" -> new RBinaryInst("slt", getReg(inst), getReg(inst.lhs()), getReg(inst.rhs()), curBlock);
            case ">=" -> {
                Reg reg = new VirtReg();
                new RBinaryInst("slt", reg, getReg(inst.lhs()), getReg(inst.rhs()), curBlock);
                new IBinaryInst("xori", getReg(inst), reg, new Imm(1), curBlock);
            }
            case "<=" -> {
                Reg reg = new VirtReg();
                new RBinaryInst("sgt", reg, getReg(inst.lhs()), getReg(inst.rhs()), curBlock);
                new IBinaryInst("xori", getReg(inst), reg, new Imm(1), curBlock);
            }
            case "!=" -> {
                Reg reg = new VirtReg();
                new RBinaryInst("sub", reg, getReg(inst.lhs()), getReg(inst.rhs()), curBlock);
                new IBinaryInst("snez", getReg(inst), reg, curBlock);
            }
            case "==" -> {
                Reg reg = new VirtReg();
                new RBinaryInst("sub", reg, getReg(inst.lhs()), getReg(inst.rhs()), curBlock);
                new IBinaryInst("seqz", getReg(inst), reg, curBlock);
            }
        }
    }

    @Override
    public void visit(src.ir.inst.LoadInst inst) {
        if (inst.ptr() instanceof Const c) { // global variable and string
            var reg = new VirtReg();
            new LuiInst(reg, new Relocation("hi", (Global) c.ASMoperand), curBlock);
            new LoadInst(((PtrType) inst.ptr().type).baseType.size, getReg(inst), reg, new Relocation("lo", (Global) c.ASMoperand), curBlock);
        } else if (inst.ptr().ASMoperand instanceof Imm i && i.ifUsedInStack) {
            new LoadInst(((PtrType) inst.ptr().type).baseType.size, getReg(inst), sp, i, curBlock);
        } else {
            new LoadInst(((PtrType) inst.ptr().type).baseType.size, getReg(inst), getReg(inst.ptr()), new Imm(0), curBlock);
        }
    }

    @Override
    public void visit(MoveInst inst) {
        getVal(getReg(inst.dest()), inst.src());
    }

    @Override
    public void visit(PhiInst inst) {
    }

    @Override
    public void visit(src.ir.inst.RetInst inst) {
        if (!inst.operands.isEmpty()) {
            getVal(a0, inst.value());
        }
    }

    @Override
    public void visit(src.ir.inst.StoreInst inst) {
        if (inst.ptr() instanceof Const c) { // global variable and string
            var reg = new VirtReg();
            new LuiInst(reg, new Relocation("hi", (Global) c.ASMoperand), curBlock);
            new StoreInst(inst.value().type.size, getReg(inst.value()), reg, new Relocation("lo", (Global) c.ASMoperand), curBlock);
        } else if (inst.ptr().ASMoperand instanceof Imm i && i.ifUsedInStack) {
            new StoreInst(inst.value().type.size, getReg(inst.value()), sp, i, curBlock);
        } else {
            new StoreInst(inst.value().type.size, getReg(inst.value()), getReg(inst.ptr()), new Imm(0), curBlock);
        }
    }

    public boolean isImm(int value) {
        return value < (1 << 11) && value >= -(1 << 11);
    }

    public Integer getConst(Entity e) {
        if (e instanceof IntConst) return ((IntConst) e).value;
        else if (e instanceof NullConst) return 0;
        return null;
    }

    private Reg getReg(Entity e) {
        if (e.ASMoperand != null) return (Reg) e.ASMoperand;
        var val = getConst(e);
        if (val != null) {
            if (val == 0) {
                e.ASMoperand = zero;
            } else {
                var reg = new VirtReg(e.type.size);
                new LiInst(reg, new Imm(val), curBlock);
                return reg;
            }
            return (Reg) e.ASMoperand;
        }
        e.ASMoperand = new VirtReg(e.type.size);
        return (Reg) e.ASMoperand;
    }

    private void getVal(Reg dest, Entity src) {
        var val = getConst(src);
        if (val != null) {
            new LiInst(dest, new Imm(val), curBlock);
        } else {
            new MvInst(dest, getReg(src), curBlock);
        }
    }

    private void builtCFG(Block block) {
        ASMBlock asm = (ASMBlock) block.ASMoperand;
        for (var p : block.pred) {
            asm.pred.add((ASMBlock) p.ASMoperand);
        }
        for (var n : block.succ) {
            asm.succ.add((ASMBlock) n.ASMoperand);
        }
    }
}
