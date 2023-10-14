package src.asm;

import src.asm.inst.Inst;
import src.asm.operand.Operand;
import src.asm.operand.Reg;
import src.backend.utils.LANode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class ASMBlock extends Operand {
    public String name;
    public LinkedList<Inst> insts = new LinkedList<>();
    public LANode laNode;
    public ArrayList<ASMBlock> pred = new ArrayList<>();
    public ArrayList<ASMBlock> succ = new ArrayList<>();

    public HashSet<Reg> liveIn = new HashSet<>();
    public HashSet<Reg> liveOut = new HashSet<>();


    public ASMBlock(String name) {
        this.name = name;
    }

    public void addInst(Inst inst) {
        this.insts.add(inst);
    }
}
