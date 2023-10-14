package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.Reg;

import java.util.HashSet;

// beqz 指令用于将寄存器与零进行比较，并根据比较结果进行条件分支
// 如果寄存器 rs 的值等于零，则跳转到指定的相对偏移量处执行相应的代码。
// beqz rs offset
public class BeqzInst extends Inst {
    public ASMBlock dest;

    public BeqzInst(Reg rs, ASMBlock dest, ASMBlock belonging) {
        super(belonging);
        this.rs1 = rs;
        this.dest = dest;
    }


    @Override
    public HashSet<Reg> uses() {
        var use = new HashSet<Reg>();
        use.add(rs1);
        return use;
    }

    @Override
    public void replaceUse(Reg pre, Reg cur) {
        if (rs1 == pre) rs1 = cur;
    }

    @Override
    public String toString() {
        return "beqz %s, %s".formatted(rs1, dest.name);
    }
}
