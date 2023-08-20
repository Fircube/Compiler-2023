package src.ir.type;

import jdk.jfr.Percentage;

public class IntType extends BaseType {
    public int bitLen;

    public IntType(int bitLen) {
        super((bitLen - 1) / 8 + 1);
        this.bitLen = bitLen;
    }

    @Override
    public String toString() {
        return "i" + bitLen;
    }
}
