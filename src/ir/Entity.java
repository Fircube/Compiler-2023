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

    public String name() {
        return name;
    }

    public String nameWithType() {
        return type + " " + name();
    }
}
