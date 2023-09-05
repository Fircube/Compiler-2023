package src.asm.operand;

public class Imm extends Operand {
    public int value;

    public Imm(int value) {
        this.value = value;
    }

    public String toString() {
        return Integer.toString(value);
    }
}
