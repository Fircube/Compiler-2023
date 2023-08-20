package src.ir.constant;

import src.ir.type.IntType;

public class IntConst extends Const {
    public int value;

    public IntConst(int value) {
        super(new IntType(32), null);
        this.value = value;
    }

    public IntConst(int value, int bitLen) {
        super(new IntType(bitLen), null);
        this.value = value;
    }

    @Override
    public String name() {
        return String.valueOf(value);
    }

}
