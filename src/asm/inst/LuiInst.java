package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.*;

import java.util.HashSet;

// Lui 是一个加载立即数的指令，用于将一个 20 位的立即数左移 12 位并存储到目标寄存器的高位 20 位中
// Lui rd, imm
public class LuiInst extends Inst {
    public Imm imm;

    public LuiInst(Reg rd, Imm imm, ASMBlock belonging) {
        super(belonging);
        this.rd = rd;
        this.imm = imm;
    }

    public HashSet<Reg> defs() {
        var def = new HashSet<Reg>();
        def.add(rd);
        return def;
    }


    @Override
    public void replaceDef(Reg pre, Reg cur) {
        if (rd == pre) rd = cur;
    }

    @Override
    public String toString() {
        return "lui %s, %s".formatted(rd, imm);
    }
}
