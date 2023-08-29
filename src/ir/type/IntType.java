package src.ir.type;

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
