package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.*;

import java.util.HashSet;

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
    public HashSet<Reg> defs() {
        var def = new HashSet<Reg>();
        def.add(rd);
        return def;
    }

    @Override
    public HashSet<Reg> uses() {
        var use = new HashSet<Reg>();
        use.add(rs1);
        return use;
    }

    @Override
    public void replaceDef(Reg pre, Reg cur) {
        if (rd == pre) rd = cur;
    }

    @Override
    public void replaceUse(Reg pre, Reg cur) {
        if (rs1 == pre) rs1 = cur;
    }

    @Override
    public String toString() {
        if (this.imm == null) return "%s %s, %s".formatted(op, rd, rs1);
        else return "%s %s, %s, %s".formatted(op, rd, rs1, imm);
    }
}
