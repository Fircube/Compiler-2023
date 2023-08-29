package src.asm.operand;

public class GlobalStr extends Global {
    public String val;

    public GlobalStr(String name, String val) {
        super(name);
        this.val = val;
    }

    public String formatted() {
        return val.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\"", "\\\"")
                .replace("\0", "");
    }
}
