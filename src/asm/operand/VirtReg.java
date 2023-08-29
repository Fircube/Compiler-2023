package src.asm.operand;

public class VirtReg extends Reg {
    public int size;

    public VirtReg() {
        this.size = 4;
    }

    public VirtReg(int size) {
        this.size = size;
    }
}
