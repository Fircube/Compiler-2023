package src.backend;

import src.asm.ASMFunction;
import src.asm.inst.IBinaryInst;
import src.asm.inst.LoadInst;
import src.asm.inst.StoreInst;
import src.asm.operand.Imm;
import src.utils.scope.GlobalScope;

public class StackAllocator {
    public GlobalScope globalScope;
    public ASMFunction curFunc;

    public StackAllocator(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    public void run() {
        for (var func : globalScope.funcs) {
            curFunc = func;
            func.stackCnt += func.allocaCnt + func.paramCnt + func.spilledCnt;
            for (var b : func.blocks) {
                for (var i : b.insts) {
                    if (i instanceof LoadInst l && l.offset.ifUsedInStack) {
                        l.offset = replace(l.offset);
                    } else if (i instanceof StoreInst s && s.offset.ifUsedInStack) {
                        s.offset = replace(s.offset);
                    } else if (i instanceof IBinaryInst ib && ib.imm != null && ib.imm.ifUsedInStack) {
                        ib.imm = replace(ib.imm);
                    }
                }
            }
        }
    }

    private Imm replace(Imm s) {
        int offset = switch (s.type) {
            case decSp -> -curFunc.stackCnt;
            case incSp -> curFunc.stackCnt;
            case putArg -> s.value + 1;
            case getArg -> curFunc.stackCnt + s.value;
            case alloca -> curFunc.paramCnt + s.value + 1;
            case spill -> curFunc.paramCnt + curFunc.allocaCnt + s.value + 1;
        };
        return new Imm(offset << 2);
    }

}
