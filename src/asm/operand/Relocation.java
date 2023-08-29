package src.asm.operand;

// 返回符号的高位地址。它将符号的高位地址提取出来，并将其作为立即数操作数传递给指令。通常与 LUI 指令结合使用，用于加载符号的高位地址到目标寄存器中。
// %hi(symbol)
// 返回符号的低位地址。它将符号的低位地址提取出来，并将其作为立即数操作数传递给指令。通常与 ADDI 指令结合使用，用于加载符号的低位地址到目标寄存器中。
// %lo(symbol)
public class Relocation extends Imm{
    public String type;
    public Global obj;

    public Relocation(String type, Global obj) {
        super(0);
        this.type = type;
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "%%%s(%s)".formatted(type, obj);
    }
}
