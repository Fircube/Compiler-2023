package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.PhysReg;
import src.asm.operand.Reg;

import java.util.HashSet;

public class RBinaryInst extends Inst{
    public String op;

    public RBinaryInst(String op, Reg rd, Reg rs1, Reg rs2, ASMBlock belonging) {
        super(belonging);
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }
    public HashSet<Reg> defs() {
        var def = new HashSet<Reg>();
        def.add(rd);
        return def;
    }

    @Override
    public HashSet<Reg> uses() {
        var use = new HashSet<Reg>();
        use.add(rs1);
        use.add(rs2);
        return use;
    }

    @Override
    public void replaceDef(Reg pre, Reg cur) {
        if (rd == pre) rd = cur;
    }

    @Override
    public void replaceUse(Reg pre, Reg cur) {
        if (rs1 == pre) rs1 = cur;
        if (rs2 == pre) rs2 = cur;
    }


    @Override
    public String toString() {
        return "%s %s, %s, %s".formatted(op, rd, rs1, rs2);
    }
}
