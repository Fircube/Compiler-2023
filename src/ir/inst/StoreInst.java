package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.PtrType;

public class StoreInst extends Inst {
    public Entity value;
    public Entity ptr;

    public StoreInst(Entity value, Entity ptr, Block belonging) {
        super(((PtrType) ptr.type).baseType, "store", belonging);
        this.value = value;
        this.ptr = ptr;
    }

    @Override
    public String toString() {
        return "store %s %s, %s, align %d".formatted(this.type, value.name(), ptr.nameWithType(), value.type.size);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
