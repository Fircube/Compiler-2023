package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.*;

// 将立即数（immediate）加载到目标寄存器rd中。立即数是一个常数值
// li rd, imm
public class LiInst extends Inst {
    public Reg rd;
    public Imm imm;

    public LiInst(Reg rd, Imm imm, ASMBlock belonging) {
        super(belonging);
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "li %s, %s".formatted(rd, imm);
    }
}
