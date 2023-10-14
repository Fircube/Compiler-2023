package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.Reg;

import java.util.HashSet;

// 将源寄存器rs的值移动到目标寄存器rd中。这是一条寄存器之间的数据传输指令
// mv rd, rs
public class MvInst extends Inst {

    public MvInst(Reg rd, Reg rs, ASMBlock belonging) {
        super(belonging);
        this.rd = rd;
        this.rs1 = rs;
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
        return "mv %s, %s".formatted(rd, rs1);
    }
}
