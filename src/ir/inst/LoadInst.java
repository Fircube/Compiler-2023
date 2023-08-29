package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.PtrType;

public class LoadInst extends Inst {
    public Entity ptr;

    public LoadInst(String name, Entity ptr, Block belonging) {
        super(((PtrType) ptr.type).baseType, name, belonging);
        this.ptr = ptr;
    }

    @Override
    public String toString() {
        return "%s = load %s, %s, align %d".formatted(this.name(), this.type, ptr.nameWithType(), ptr.type.size);

    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
