package src.asm.inst;

import src.asm.ASMBlock;

// 返回指令，用于从子函数返回到调用者
// ret
public class RetInst extends Inst {
    public RetInst(ASMBlock belonging) {
        super(belonging);
    }

    @Override
    public String toString() {
        return "ret";
    }
}
