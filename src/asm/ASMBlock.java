package src.asm;

import src.asm.inst.Inst;
import src.asm.operand.Operand;

import java.util.LinkedList;

public class ASMBlock extends Operand {
    public String name;
    public LinkedList<Inst> insts = new LinkedList<>();

    public ASMBlock(String name) {
        this.name = name;
    }

    public void addInst(Inst inst) {
        this.insts.add(inst);
    }
}
