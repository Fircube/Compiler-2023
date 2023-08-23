package src.ir.constant;

import src.ir.Entity;
import src.ir.type.*;

public class GlobalVar extends Const { // A global variable is essentially a pointer
    public Entity init;

    public GlobalVar(BaseType type, String name) {
        super(new PtrType(type), name);
    }

    public String toString() {
        if(init!=null)
            return "%s = global %s %s, align %d".formatted(name, ((PtrType) type).baseType, init.name(), type.size);
        return "%s = global %s %s, align %d".formatted(name, ((PtrType) type).baseType, init, type.size);
    }
}
