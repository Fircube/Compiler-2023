package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.BaseType;

import java.util.ArrayList;

public class PhiInst extends Inst {
    public int idx;

    public PhiInst(int idx, BaseType type, String name, Block belonging) {
        super(type, name, null);
        this.idx = idx;
        if (this.belonging != belonging) {
            this.belonging = belonging;
            if (belonging != null) {
                belonging.phiInsts.add(this);
            }
        }
    }

    public void addBranch(Entity value, Block block) {
        addOperand(value);
        addOperand(block);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("%s = phi %s ".formatted(name, type));
        if (!operands.isEmpty()) {
            s.append("[%s, %s]".formatted(operands.get(0).name(), "%" + operands.get(1).name()));
            for (int i = 2; i < operands.size(); i += 2) {
                s.append(", ");
                s.append("[%s, %s]".formatted(operands.get(i).name(), "%" + operands.get(i + 1).name()));
            }
        }
        return s.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
