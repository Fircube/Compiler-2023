package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.Reg;

public class RBinaryInst extends Inst{
    public String op;

    public RBinaryInst(String op, Reg rd, Reg rs1, Reg rs2, ASMBlock belonging) {
        super(belonging);
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    @Override
    public String toString() {
        return "%s %s, %s, %s".formatted(op, rd, rs1, rs2);
    }
}
