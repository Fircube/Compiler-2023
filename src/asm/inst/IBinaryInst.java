package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.*;

public class IBinaryInst extends Inst {
    public String op;
    public Reg rd, rs;
    public Imm imm;

    public IBinaryInst(String op, Reg rd, Reg rs, ASMBlock belonging) {
        super(belonging);
        this.op = op;
        this.rd = rd;
        this.rs = rs;
        this.imm = null;
    }

    public IBinaryInst(String op, Reg rd, Reg rs, Imm imm, ASMBlock belonging) {
        super(belonging);
        this.op = op;
        this.rd = rd;
        this.rs = rs;
        this.imm = imm;
    }

    @Override
    public String toString() {
        if (this.imm == null) return "%s %s, %s".formatted(op, rd, rs);
        else return "%s %s, %s, %s".formatted(op, rd, rs, imm);
    }
}
