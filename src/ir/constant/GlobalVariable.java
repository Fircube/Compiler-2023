package src.ir.constant;

import src.ir.Entity;
import src.ir.type.*;

public class GlobalVariable extends Const { // A global variable is essentially a pointer
    public Entity init;

    public GlobalVariable(BaseType type, String name) {
        super(new PtrType(type), name);
    }

    public String toString() {
        if (init != null)
            return "%s = global %s %s, align %d".formatted(name, ((PtrType) type).baseType, init.name(), type.size);
        return "%s = global %s zeroinitializer, align %d".formatted(name, ((PtrType) type).baseType, type.size);
    }
}
