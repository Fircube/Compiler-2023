package src.asm.operand;

public class VirtReg extends Reg {
    public int size;
    public int offset;

    public VirtReg(int offset) {
        this.size = 4;
        this.offset = offset;
    }

    public VirtReg(int size, int offset) {
        this.size = size;
        this.offset = offset;
    }

    @Override
    public String toString() {
        return offset + "(fp)";
    }
}
