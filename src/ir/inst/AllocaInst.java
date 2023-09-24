package src.ir.inst;

import src.ir.Block;
import src.ir.IRVisitor;
import src.ir.type.BaseType;
import src.ir.type.PtrType;

public class AllocaInst extends Inst {
    public BaseType baseType;

    public AllocaInst(BaseType baseType, String name, Block belonging) {
        super(new PtrType(baseType), name, belonging);
        this.baseType = baseType;
    }

    @Override
    public String toString() {
        return "%s = alloca %s, align %d".formatted(name(), baseType, baseType.size);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
