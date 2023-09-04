package src.ir;

import src.asm.operand.*;
import src.ir.constant.IntConst;
import src.ir.constant.NullConst;
import src.ir.type.BaseType;

public class Entity {
    public BaseType type;
    public String name;

    public Operand operand = null;

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
