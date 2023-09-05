package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.*;

// 将源寄存器rs中的字节、半字或字存储到内存中，根据指令后缀（b、h或w），可以存储不同大小的数据
// s{b, h, w} rs, symbol
public class StoreInst extends Inst {
    public int size;
    public Imm offset;

    public StoreInst(int size, Reg rs, Reg rd, Imm offset, ASMBlock belonging) {
        super(belonging);
        this.size = size;
        this.rs1 = rs;
        this.rd = rd;
        this.offset = offset;
    }

    @Override
    public String toString() {
        if (size == 1) return "sb %s, %s(%s)".formatted(rs1, offset, rd);
        else return "sw %s, %s(%s)".formatted(rs1, offset, rd);
    }
}
