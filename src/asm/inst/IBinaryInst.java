package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.*;

// 有立即数的二元运算
// {addi, slli, xori } rd, rs, imm
// 执行有符号数的不等于零和等于0比较
// {snez, sqez} rd, rs
public class IBinaryInst extends Inst {
    public String op;
    public Imm imm;

    public IBinaryInst(String op, Reg rd, Reg rs, ASMBlock belonging) {
        super(belonging);
        this.op = op;
        this.rd = rd;
        this.rs1 = rs;
        this.imm = null;
    }

    public IBinaryInst(String op, Reg rd, Reg rs, Imm imm, ASMBlock belonging) {
        super(belonging);
        this.op = op;
        this.rd = rd;
        this.rs1 = rs;
        this.imm = imm;
    }

    @Override
    public String toString() {
        if (this.imm == null) return "%s %s, %s".formatted(op, rd, rs1);
        else return "%s %s, %s, %s".formatted(op, rd, rs1, imm);
    }
}
