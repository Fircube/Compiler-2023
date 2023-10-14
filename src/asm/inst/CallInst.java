package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.ASMFunction;
import src.asm.operand.PhysReg;
import src.asm.operand.Reg;

import java.util.HashSet;

// 调用指令，用于调用子函数。将当前指令地址加上偏移量作为返回地址保存，并跳转到目标地址。
// call offset
public class CallInst extends Inst {
    public ASMFunction func;

    public CallInst(ASMFunction func, ASMBlock belonging) {
        super(belonging);
        this.func = func;
    }

    @Override
    public HashSet<Reg> defs() {
        return new HashSet<>(PhysReg.callerSaved);
    }

    @Override
    public HashSet<Reg> uses() {
        var use = new HashSet<Reg>();
        if (func.paramCnt != 0) {
            for (int i = 0; i < 8; ++i) {
                use.add(PhysReg.regs.get("a"+i));
            }
        } else {
            for (int i = 0; i < func.params.size(); ++i) {
                use.add(PhysReg.regs.get("a"+i));
            }
        }
        return use;
    }

    @Override
    public String toString() {
        return "call " + func.name;
    }
}
