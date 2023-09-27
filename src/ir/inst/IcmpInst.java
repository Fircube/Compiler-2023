package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.IntType;

public class IcmpInst extends Inst {
    public String op;

    public IcmpInst(String op, Entity lhs, Entity rhs, String name, Block belonging) {
        super(new IntType(1), name, belonging);
        this.op = op;
        addOperand(lhs);
        addOperand(rhs);
    }

    public Entity lhs(){
        return operands.get(0);
    }

    public Entity rhs(){
        return operands.get(1);
    }

    @Override
    public String toString() {
        String order = switch (op) {
            case ">" -> "sgt";
            case "<" -> "slt";
            case ">=" -> "sge";
            case "<=" -> "sle";
            case "!=" -> "ne";
            case "==" -> "eq";
            default -> null;
        };
        return "%s = icmp %s %s, %s".formatted(name(), order, lhs().nameWithType(), rhs().name());
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
