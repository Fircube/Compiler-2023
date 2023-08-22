package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.IntType;

public class IcmpInst extends Inst {
    public String op;
    public Entity lhs, rhs;

    public IcmpInst(String op, Entity lhs, Entity rhs, String name, Block belonging) {
        super(new IntType(1), name,belonging);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return "%s = icmp %s %s, %s".formatted(name(), op, lhs.nameWithType(), rhs.nameWithType());
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}