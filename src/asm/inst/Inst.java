package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.Reg;

// 当只有一个rs默认使用rs1
public class Inst {
    public Reg rs1, rs2, rd;

    public Inst(ASMBlock belonging) {
        addToBlock(belonging);
    }

    public void addToBlock(ASMBlock belonging) {
        if (belonging != null) belonging.addInst(this);
    }
}
