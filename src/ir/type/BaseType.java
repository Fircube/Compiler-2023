package src.ir.type;

public abstract class BaseType {
    public int size;

    public BaseType(int size) {
        this.size = size;
    }

    public abstract String toString();
}
