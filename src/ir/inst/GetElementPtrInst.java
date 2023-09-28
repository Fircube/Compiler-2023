package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.BaseType;
import src.ir.type.PtrType;

public class GetElementPtrInst extends Inst {
    public GetElementPtrInst(BaseType retType, String name, Block belonging, Entity ptr, Entity... idx) {
        super(retType, name, belonging);
        addOperand(ptr);
        for (var i : idx) {
            addOperand(i);
        }
    }

    public Entity ptr() {
        return operands.get(0);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(new StringBuilder("%s = getelementptr inbounds %s, %s, ".formatted(name(), ((PtrType) ptr().type).baseType, ptr().nameWithType())));
        if (operands.size() > 1) {
            s.append(operands.get(1).nameWithType());
            for (int i = 2; i < operands.size(); ++i) {
                s.append(", ");
                s.append(operands.get(2).nameWithType());
            }
        }
        return s.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
