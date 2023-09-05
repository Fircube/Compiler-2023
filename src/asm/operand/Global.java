package src.asm.operand;

// e.g. .asciz .word .byte .zero
public class Global extends Operand {
    public String name;

    Global(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
