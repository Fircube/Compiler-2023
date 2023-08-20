package src.ir;

import src.ir.type.BaseType;

public class Entity {
    public BaseType type;
    public String name;

    public Entity(BaseType type, String name) {
        this.type = type;
        this.name = name;
    }

    public String name() {
        return name;
    }

    public String nameWithType() {
        return type + " " + name;
    }

}
