package src.asm;

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
import src.utils.scope.GlobalScope;

import java.util.Objects;

public class InstSelection implements IRVisitor {
    public GlobalScope globalScope;

    ASMBlock curBlock;
    ASMFunction curFunc;

    private final PhysReg sp = PhysReg.regMap.get("sp");
    private final PhysReg ra = PhysReg.regMap.get("ra");


    public InstSelection(GlobalScope globalScope) {
        this.globalScope = globalScope;

        for (String key : globalScope.globalVars.keySet()) {
            GlobalVariable v = globalScope.globalVars.get(key);
            Integer init = v.init.getConst();
            v.operand = new GlobalVar(v.name.substring(1), v.type.size, Objects.requireNonNullElse(init, 0));
            globalScope.gVars.add((GlobalVar) v.operand);
        }
        for (String key : globalScope.stringConst.keySet()) {
            StringConst str = globalScope.stringConst.get(key);
            str.operand = new GlobalStr(str.name.substring(1), str.value);
            globalScope.gStrs.add((GlobalStr) str.operand);
        }

        for (String key : globalScope.buildinFunc.keySet()) {
            Function buildinFunc = globalScope.functions.get(key);
            buildinFunc.operand = new ASMFunction(buildinFunc.name.substring(1));
        }
        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            ASMFunction asmFunc = new ASMFunction(func.name.substring(1));
            func.operand = asmFunc;
            for (var param : func.params) {
                param.operand = new VirtReg();
                asmFunc.params.add((Reg) param.operand);
            }
            globalScope.funcs.add((ASMFunction) func.operand);
        }

        for (String key : globalScope.functions.keySet()) {
            globalScope.functions.get(key).accept(this);
        }
    }

    @Override
    public void visit(Function func) {
        curFunc = (ASMFunction) func.operand;

        for (var block : func.blocks) {
            block.operand = new ASMBlock(block.name);
            curFunc.blocks.add((ASMBlock) block.operand);
        }
        curFunc.entryBlock = (ASMBlock) func.entryBlock.operand;

        curBlock = curFunc.entryBlock;
        new MvInst(curFunc.cacheRa, ra, curBlock);
        for (var reg : PhysReg.calleeSaved) {
            var cache = new VirtReg();
            curFunc.cacheRegs.add(cache);
            new MvInst(cache, reg, curBlock);
        }

        for (int i = 0; i < func.params.size(); ++i) {
            if (i < 8) {
                new MvInst(curFunc.params.get(i), PhysReg.regMap.get("a" + i), curBlock);
            } else {
                new LoadInst(4, curFunc.params.get(i), sp, new Imm(i - 8, true), curBlock);
            }
        }

        for (var block : func.blocks) {
            block.accept(this);
        }
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
                switch (icmp.op) {
                    case ">" ->
                            new src.asm.inst.BrInst("bgt", getReg(icmp.lhs), getReg(icmp.rhs), (ASMBlock) br.trueDest.operand, curBlock);
                    case "<" ->
                            new src.asm.inst.BrInst("blt", getReg(icmp.lhs), getReg(icmp.rhs), (ASMBlock) br.trueDest.operand, curBlock);
                    case ">=" ->
                            new src.asm.inst.BrInst("bge", getReg(icmp.lhs), getReg(icmp.rhs), (ASMBlock) br.trueDest.operand, curBlock);
                    case "<=" ->
                            new src.asm.inst.BrInst("ble", getReg(icmp.lhs), getReg(icmp.rhs), (ASMBlock) br.trueDest.operand, curBlock);
                    case "!=" ->
                            new src.asm.inst.BrInst("bne", getReg(icmp.lhs), getReg(icmp.rhs), (ASMBlock) br.trueDest.operand, curBlock);
                    case "==" ->
                            new src.asm.inst.BrInst("beq", getReg(icmp.lhs), getReg(icmp.rhs), (ASMBlock) br.trueDest.operand, curBlock);
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
    }

    @Override
    public void visit(AllocaInst inst) {
        inst.operand = new Imm(curFunc.allocaSize, true);
        ++curFunc.allocaSize;
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
                if (value < (1 << 11) && value >= -(1 << 11)) {
                    new IBinaryInst(op_, getReg(inst), getReg(inst.lhs), new Imm(value), curBlock);
                    return;
                }
            }
        }
        new RBinaryInst(op, getReg(inst), getReg(inst.lhs), getReg(inst.rhs), curBlock);
    }

    @Override
    public void visit(BitCastInst inst) {
        Assign(getReg(inst), inst.value);
    }

    @Override
    public void visit(src.ir.inst.BrInst inst) {
        if (inst.isJump) {
            new JumpInst((ASMBlock) inst.trueDest.operand, curBlock);
        } else {
            new BeqzInst(getReg(inst.con), (ASMBlock) inst.falseDest.operand, curBlock);
            new JumpInst((ASMBlock) inst.trueDest.operand, curBlock);
        }
    }

    @Override
    public void visit(src.ir.inst.CallInst inst) {
        for (int i = 0; i + 1 < inst.paramList.size(); ++i) {
            var param = inst.paramList.get(i + 1);
            if (i < 8) {
                Assign(PhysReg.regMap.get("a" + i), param);
            } else {
                new StoreInst(4, getReg(param), sp, new Imm(i - 8, true), curBlock);
            }
        }
        new CallInst((ASMFunction) inst.func.operand, curBlock);
        if (!(inst.type instanceof VoidType)) {
            new MvInst(getReg(inst), PhysReg.regMap.get("a0"), curBlock);
        }
    }

    @Override
    public void visit(GetElementPtrInst inst) {
    }

    @Override
    public void visit(IcmpInst inst) {
        switch (inst.op) {
            case ">" -> {
                new RBinaryInst("sgt", getReg(inst), getReg(inst.lhs), getReg(inst.rhs), curBlock);
            }
            case "<" -> {
                new RBinaryInst("slt", getReg(inst), getReg(inst.lhs), getReg(inst.rhs), curBlock);
            }
            case ">=" -> {
                var tmp = new VirtReg();
                new RBinaryInst("slt", tmp, getReg(inst.lhs), getReg(inst.rhs), curBlock);
                new IBinaryInst("xori", getReg(inst), tmp, new Imm(1), curBlock);
            }
            case "<=" -> {
                var tmp = new VirtReg();
                new RBinaryInst("sgt", tmp, getReg(inst.lhs), getReg(inst.rhs), curBlock);
                new IBinaryInst("xori", getReg(inst), tmp, new Imm(1), curBlock);
            }
            case "!=" -> {
                var tmp = new VirtReg();
                new RBinaryInst("sub", tmp, getReg(inst.lhs), getReg(inst.rhs), curBlock);
                new IBinaryInst("snez", getReg(inst), tmp, curBlock);
            }
            case "==" ->{
                var tmp = new VirtReg();
                new RBinaryInst("sub", tmp, getReg(inst.lhs), getReg(inst.rhs), curBlock);
                new IBinaryInst("seqz", getReg(inst), tmp, curBlock);
            }
        }
    }

    @Override
    public void visit(src.ir.inst.LoadInst inst) {
        if (inst.ptr instanceof Const c) { // global variable and string
            var tmp = new VirtReg();
            new LuiInst(tmp, new Relocation("hi", (Global) c.operand), curBlock);
            new LoadInst(((PtrType) inst.ptr.type).baseType.size, getReg(inst), tmp, new Relocation("lo", (Global) c.operand), curBlock);
        } else {
            if (inst.ptr.operand instanceof Imm i && i.stackRelated)
                new LoadInst(((PtrType) inst.ptr.type).baseType.size, getReg(inst), sp, i, curBlock);
            else
                new LoadInst(((PtrType) inst.ptr.type).baseType.size, getReg(inst), getReg(inst.ptr), new Imm(0), curBlock);
        }
    }

    @Override
    public void visit(PhiInst inst) {
    }

    @Override
    public void visit(src.ir.inst.RetInst inst) {
        if(inst.value!=null) Assign(PhysReg.regMap.get("a0"),inst.value);
        int i = 0;
        for (var reg : PhysReg.calleeSaved) {
            new MvInst(reg, curFunc.cacheRegs.get(i), curBlock);
            i++;
        }
        new MvInst(ra, curFunc.cacheRa, curBlock);

        new RetInst(curBlock);

    }

    @Override
    public void visit(src.ir.inst.StoreInst inst) {
        if (inst.ptr instanceof Const c) { // global variable and string
            var tmp = new VirtReg();
            new LuiInst(tmp, new Relocation("hi", (Global) c.operand), curBlock);
            new StoreInst(inst.value.type.size, getReg(inst.value), tmp, new Relocation("lo", (Global) c.operand), curBlock);
        } else {
            if (inst.ptr.operand instanceof Imm i && i.stackRelated)
                new StoreInst(inst.value.type.size, getReg(inst.value), sp, i, curBlock);
            else
                new StoreInst(inst.value.type.size, getReg(inst.value), getReg(inst.ptr), new Imm(0), curBlock);
        }
    }

    private void Assign(Reg rd, Entity rc) {
        Integer val = rc.getConst();
        if (val != null) new LiInst(rd, new Imm(val), curBlock);
        else new MvInst(rd, getReg(rc), curBlock);
    }

    private Reg getReg(Entity e) {
        if (e.operand != null) {
            return (Reg) e.operand;
        }
        var constVal = e.getConst();
        if (constVal != null) {
            if (constVal == 0) {
                e.operand = PhysReg.regMap.get("zero");
            } else {
                VirtReg reg = new VirtReg(e.type.size);
                new LiInst(reg, new Imm(constVal), curBlock);
                return reg;
            }
        } else {
            e.operand = new VirtReg(e.type.size);
        }
        return (Reg) e.operand;
    }

}
