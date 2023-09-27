package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.BaseType;

public class BitCastInst extends Inst {
    public BitCastInst(String name, BaseType toType, Entity value, Block parent) {
        super(toType, name, parent);
        addOperand(value);
    }

    public Entity value(){
        return operands.get(0);
    }

    @Override
    public String toString() {
        return "%s = bitcast %s to %s".formatted(name(), value().nameWithType(), this.type);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
