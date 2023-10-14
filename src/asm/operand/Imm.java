package src.asm.operand;

public class Imm extends Operand {
    public int value;
    public boolean ifUsedInStack;

    public enum Type {
        decSp, incSp, putArg, getArg, alloca, spill
    }

    public Type type;

    public Imm(int value) {
        this.value = value;
        this.ifUsedInStack = false;
    }

    public Imm(int value, boolean ifUsedInStack, Type type) {
        this.value = value;
        this.ifUsedInStack = ifUsedInStack;
        this.type = type;
    }

    public String toString() {
        return Integer.toString(value);
    }
}
