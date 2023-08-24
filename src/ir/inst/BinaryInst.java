package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;

public class BinaryInst extends Inst {
    public String op;
    public Entity lhs, rhs;

    public BinaryInst(String op, Entity lhs, Entity rhs, String name, Block belonging) {
        super(lhs.type, name, belonging);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
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
        return "%s = %s %s %s, %s".formatted(name(), order, type, lhs.name(), rhs.name());
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
