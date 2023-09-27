package src.ir;

import src.asm.operand.*;
import src.ir.type.BaseType;

import java.util.ArrayList;

public class Entity {
    public BaseType type;
    public String name;

    public Operand operand = null;

    public ArrayList<Entity> users = new ArrayList<>();
    public ArrayList<Entity> operands = new ArrayList<>();

    public Entity(BaseType type, String name) {
        this.type = type;
        this.name = name;
    }

    public void addOperand(Entity e) {
        this.operands.add(e);
        e.users.add(this);
    }

    public void replaceUsesTo(Entity e) {
        for (var user : users) {
            var operands = user.operands;
            for (int i = 0; i < operands.size(); ++i) {
                if (operands.get(i) == this) {
                    operands.set(i, e);
                }
            }
            e.users.add(user);
        }
        users.clear();
    }

    public String name() {
        return name;
    }

    public String nameWithType() {
        return type + " " + name();
    }
}
