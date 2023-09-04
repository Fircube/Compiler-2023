package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.operand.Reg;

// 根据条件跳转到目标地址。这些指令根据源寄存器rs的值和条件进行跳转，目标地址是当前指令地址加上偏移量。
// beqz rs offset
public class BeqzInst extends Inst{
//    public Reg rs;
    public ASMBlock dest;
    public BeqzInst(Reg rs,ASMBlock dest,ASMBlock belonging){
        super(belonging);
        this.rs1=rs;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "beqz %s, %s".formatted(rs1, dest.name);
    }
}
