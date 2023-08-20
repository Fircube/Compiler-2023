package src.ir.constant;

import src.ir.type.IntType;

public class BoolConst extends IntConst {
    public boolean value;

    public BoolConst(boolean value) {
        super(value ? 1 : 0, 1);
        this.value = value;
    }

    @Override
    public String name() {
        return value ? "1" : "0";
    }

}
