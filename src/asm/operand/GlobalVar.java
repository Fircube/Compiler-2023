package src.asm.operand;

public class GlobalVar extends Global {
    public int size;
    public int init;

    public GlobalVar(String name, int size, int init) {
        super(name);
        this.size = size;
        this.init = init;
    }
}
