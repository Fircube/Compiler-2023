package src.ir;

import src.ir.inst.*;
import src.ir.type.LabelType;
import src.middleend.DomTreeNode;

import java.util.ArrayList;
import java.util.LinkedList;

public class Block extends Entity {
    public LinkedList<Inst> insts = new LinkedList<>();
    public LinkedList<PhiInst> phiInsts = new LinkedList<>();

    public boolean terminated = false;
    public DomTreeNode domTreeNode = new DomTreeNode(this);
    public ArrayList<Block> pred = new ArrayList<>();
    public ArrayList<Block> succ = new ArrayList<>();


    public Block(String name, Function belonging) {
        super(new LabelType(), name);
        if (belonging != null) {
            belonging.blocks.add(this);
        }
    }

    public void addInst(Inst inst) {
        if (inst instanceof AllocaInst i) {
            addAllocaInst(i);
            return;
        }
        if (this.terminated) return;
        this.insts.add(inst);
        if (inst.isTerminalInst()) this.terminated = true;
    }

    public void addAllocaInst(AllocaInst allocaInst) {
        for (int i = 0; i < insts.size(); ++i) {
            var inst = insts.get(i);
            if (!(inst instanceof AllocaInst)) {
                insts.add(i, allocaInst);
                return;
            }
        }
        insts.add(allocaInst);
    }

    @Override
    public String nameWithType() {
        return type + " %" + name;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
