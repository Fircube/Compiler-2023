package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.PhysReg;
import src.asm.operand.Reg;

import java.util.HashSet;

// 返回指令，用于从子函数返回到调用者
// ret
public class RetInst extends Inst {
    public RetInst(ASMBlock belonging) {
        super(belonging);
    }

    @Override
    public HashSet<Reg> uses() {
        var use = new HashSet<Reg>();
        use.add(PhysReg.regs.get("ra"));
        return use;
    }

    @Override
    public String toString() {
        return "ret";
    }
}
