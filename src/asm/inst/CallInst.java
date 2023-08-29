package src.asm.inst;

import src.asm.ASMBlock;
import src.asm.ASMFunction;

// 调用指令，用于调用子函数。将当前指令地址加上偏移量作为返回地址保存，并跳转到目标地址。
// call offset
public class CallInst extends Inst{
    public ASMFunction func;

    public CallInst(ASMFunction func, ASMBlock belonging){
        super(belonging);
        this.func = func;
    }

    @Override
    public String toString() {
        return "call " + func.name;
    }
}
