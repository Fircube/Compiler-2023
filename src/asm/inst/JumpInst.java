package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.Reg;

import java.util.HashSet;

// 无条件跳转到目标地址。目标地址是当前指令地址加上偏移量。
// j offset
public class JumpInst extends Inst {
    public ASMBlock dest;

    public JumpInst(ASMBlock dest, ASMBlock belonging) {
        super(belonging);
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "j " + dest.name;
    }
}
