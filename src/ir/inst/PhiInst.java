package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.BaseType;

import java.util.ArrayList;

public class PhiInst extends Inst {
    public ArrayList<Entity> value = new ArrayList<>();
    public ArrayList<Entity> label = new ArrayList<>();

    public PhiInst(BaseType type, String name, Block belonging) {
        super(type, name, belonging);
    }

    public void addBranch(Entity value, Block block) {
        this.value.add(value);
        this.label.add(block);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("%s = phi %s ".formatted(name, type));
        if (!value.isEmpty()) {
            s.append("[%s, %s]".formatted(value.get(0).name(), label.get(0).name()));
            for (int i = 1; i < value.size(); ++i) {
                s.append(", ");
                s.append("[%s, %s]".formatted(value.get(i).name(), label.get(i).name()));
            }
        }
        return s.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
