package src.asm.operand;

import src.backend.utils.CGNode;

public class Reg extends Operand {

    public CGNode cgNode = new CGNode(this);
    public CGNode spilledNode = new CGNode(this);
    public Imm stackOffset;

    @Override
    public String toString() {
        if (cgNode.color == null || cgNode.color == this) return super.toString();
        return cgNode.color.toString();
    }
}
