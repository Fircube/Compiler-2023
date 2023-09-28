package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.VoidType;

public class BrInst extends Inst {
    public boolean isJump;

    public BrInst(Block dest, Block belonging) {
        super(new VoidType(), "br", belonging);
        this.isJump = true;
        addOperand(dest);
    }

    public BrInst(Entity con, Block trueDest, Block falseDest, Block belonging) {
        super(new VoidType(), "br", belonging);
        this.isJump = false;
        addOperand(con);
        addOperand(trueDest);
        addOperand(falseDest);
    }

    public Block dest() {
        return (Block) operands.get(0);
    }

    public Entity con() {
        return operands.get(0);
    }

    public Block trueDest() {
        return (Block) operands.get(1);
    }

    public Block falseDest() {
        return (Block) operands.get(2);
    }

    public boolean isTerminalInst() {
        return true;
    }

    @Override
    public String toString() {
        if (isJump) return "br %s".formatted(dest().nameWithType());
        return "br %s, %s, %s".formatted(con().nameWithType(), trueDest().nameWithType(), falseDest().nameWithType());
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
