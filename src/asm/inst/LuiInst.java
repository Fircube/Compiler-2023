package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.*;

public class LuiInst extends Inst{
    public Reg rd;
    public Imm imm;

    public LuiInst(Reg rd, Imm imm, ASMBlock belonging) {
        super(belonging);
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "lui %s, %s".formatted(rd, imm);
    }
}
