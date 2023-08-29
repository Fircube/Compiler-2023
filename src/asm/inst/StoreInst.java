package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.*;

// 将源寄存器rt中的字节、半字或字存储到存储器中。根据指令后缀（b、h或w），可以存储不同大小的数据
// s{b, h, w} rd, symbol, rt
public class StoreInst extends Inst {
    public int size;
    public Reg rd, rs;
    public Imm offset;

    public StoreInst(int size, Reg rs, Reg rd, Imm offset, ASMBlock belonging) {
        super(belonging);
        this.size = size;
        this.rs = rs;
        this.rd = rd;
        this.offset = offset;
    }

    @Override
    public String toString() {
        if (size == 1) return "sb %s, %s(%s)".formatted(rs, offset, rd);
        else return "sw %s, %s(%s)".formatted(rs, offset, rd);
    }
}
