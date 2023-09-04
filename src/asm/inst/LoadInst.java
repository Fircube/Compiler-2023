package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.*;

// 从存储器中加载字节、半字或字到目标寄存器rd中。根据指令后缀（b、h或w），可以加载不同大小的数据
// l{b, h, w} rd, symbol
public class LoadInst extends Inst {
    public int size;
//    public Reg rd, rs;
    public Imm offset;

    public LoadInst(int size, Reg rd, Reg rs, Imm offset, ASMBlock belonging) {
        super(belonging);
        this.size = size;
        this.rd = rd;
        this.rs1 = rs;
        this.offset = offset;
    }

    @Override
    public String toString() {
        if (size == 1) return "lb %s, %s(%s)".formatted(rd, offset, rs1);
        else return "lw %s, %s(%s)".formatted(rd, offset, rs1);
    }

}
