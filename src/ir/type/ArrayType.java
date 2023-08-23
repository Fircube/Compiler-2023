package src.ir.type;

public class ArrayType extends BaseType {
    public BaseType type;
    public int len;

    public ArrayType(BaseType type, int len) {
        super(type.size * len);
        this.type = type;
        this.len = len;
    }

    @Override
    public String toString() {
        return "[%d x %s]".formatted(len, type);
    }
}
