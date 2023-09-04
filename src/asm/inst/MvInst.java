package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.Reg;

// 将源寄存器rs的值移动到目标寄存器rd中。这是一条寄存器之间的数据传输指令
// mv rd, rs
public class MvInst extends Inst{
//    public Reg rd, rs;

    public MvInst(Reg rd, Reg rs, ASMBlock belonging) {
        super(belonging);
        this.rd = rd;
        this.rs1 = rs;
    }

    @Override
    public String toString() {
        return "mv %s, %s".formatted(rd, rs1);
    }
}
