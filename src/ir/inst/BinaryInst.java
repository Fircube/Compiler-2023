package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;

public class BinaryInst extends Inst {
    public String op;
    public Entity lhs, rhs;

    public BinaryInst(String op,Entity lhs, Entity rhs,  String name, Block belonging) {
        super(lhs.type, name, belonging);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return "%s = %s %s %s, %s".formatted(name(), op, type, lhs.name(), rhs.name());
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
