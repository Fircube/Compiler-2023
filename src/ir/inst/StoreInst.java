package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.PtrType;

public class StoreInst extends Inst {
    public StoreInst(Entity value, Entity ptr, Block belonging) {
        // value can be null
        super(((PtrType) ptr.type).baseType, "store", belonging);
        addOperand(value);
        addOperand(ptr);
    }

    public Entity value() {
        return operands.get(0);
    }

    public Entity ptr() {
        return operands.get(1);
    }

    @Override
    public String toString() {
        return "store %s %s, %s, align %d".formatted(this.type, value().name(), ptr().nameWithType(), value().type.size);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
