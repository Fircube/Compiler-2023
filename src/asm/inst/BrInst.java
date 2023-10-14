package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.Reg;

import java.util.HashSet;

// 根据条件跳转到目标地址。这些指令根据源寄存器rs和rt的值以及条件进行跳转，目标地址是当前指令地址加上偏移量。
// {bgt, blt, bge, ble, beq, bne} rs, rt, offset
public class BrInst extends Inst {
    public String op;
    public ASMBlock dest;

    public BrInst(String op, Reg rs, Reg rt, ASMBlock dest, ASMBlock belonging) {
        super(belonging);
        this.op = op;
        this.rs1 = rs;
        this.rs2 = rt;
        this.dest = dest;
    }

    @Override
    public HashSet<Reg> uses() {
        var use = new HashSet<Reg>();
        use.add(rs1);
        use.add(rs2);
        return use;
    }

    @Override
    public void replaceUse(Reg pre, Reg cur) {
        if (rs1 == pre) rs1 = cur;
        if (rs2 == pre) rs2 = cur;
    }


    @Override
    public String toString() {
        return "%s %s, %s, %s".formatted(op, rs1, rs2, dest.name);
    }
}
