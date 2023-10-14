package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.Reg;

import java.util.HashSet;

// 当只有一个rs默认使用rs1
public class Inst {
    public Reg rs1, rs2, rd;

    public Inst(ASMBlock belonging) {
        addToBlock(belonging);
    }

    public void addToBlock(ASMBlock belonging) {
        if (belonging != null) belonging.addInst(this);
    }

    public HashSet<Reg> defs() {
        return new HashSet<Reg>();
    }

    public HashSet<Reg> uses() {
        return new HashSet<Reg>();
    }

    public void replaceDef(Reg pre, Reg cur) {
    }

    public void replaceUse(Reg pre, Reg cur) {
    }
}
