package src.ir.constant;

import src.ir.type.IntType;
import src.ir.type.PtrType;

public class NullConst extends Const {
    public NullConst() {
        super(new PtrType(new IntType(32)), null);
    }

    @Override
    public String name() {
        return "null";
    }
}
