package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;

public class BinaryInst extends Inst {
    public String op;

    public BinaryInst(String op, Entity lhs, Entity rhs, String name, Block belonging) {
        super(lhs.type, name, belonging);
        this.op = op;
        addOperand(lhs);
        addOperand(rhs);
    }

    public Entity lhs() {
        return operands.get(0);
    }

    public Entity rhs() {
        return operands.get(1);
    }

    @Override
    public String toString() {
        String order = switch (op) {
            case "+" -> "add";
            case "-" -> "sub";
            case "*" -> "mul";
            case "/" -> "sdiv";
            case "%" -> "srem";
            case "<<" -> "shl";
            case ">>" -> "ashr";
            case "&" -> "and";
            case "|" -> "or";
            case "^" -> "xor";
            default -> null;
        };
        return "%s = %s %s %s, %s".formatted(name(), order, type, lhs().name(), rhs().name());
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
