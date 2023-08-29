package src.ir.inst;

import src.ir.Block;
import src.ir.IRVisitor;
import src.ir.type.BaseType;
import src.ir.type.PtrType;

public class AllocaInst extends Inst {
    BaseType type;

    public AllocaInst(BaseType type, String name, Block belonging) {
        super(new PtrType(type), name, belonging);
        this.type = type;
    }

    @Override
    public String toString() {
        return "%s = alloca %s, align %d".formatted(name(), type, type.size);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
