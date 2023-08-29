package src.asm.inst;


import src.asm.ASMBlock;

public class Inst {
    public Inst(ASMBlock belonging) {
        addToBlock(belonging);
    }

    public void addToBlock(ASMBlock belonging) {
        if (belonging != null) belonging.addInst(this);
    }

}
