package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.VoidType;

public class RetInst extends Inst {
    public RetInst(Block belonging) {
        super(new VoidType(), "ret", belonging);
    }

    public RetInst(Entity value, Block belonging) {
        super(value.type, "ret", belonging);
        addOperand(value);
    }

    public Entity value() {
        return operands.get(0);
    }

    public boolean isTerminalInst() {
        return true;
    }

    @Override
    public String toString() {
        if (this.type instanceof VoidType) {
            return "ret void";
        } else {
            return "ret %s".formatted(value().nameWithType());
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
