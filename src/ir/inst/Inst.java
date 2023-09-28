package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.BaseType;

public abstract class Inst extends Entity {
    public Block belonging;

    public Inst(BaseType type, String name, Block belonging) {
        super(type, name);
        addToBlock(belonging);
    }

    public void addToBlock(Block belonging) {
        if (this.belonging != belonging) {
            this.belonging = belonging;
            if (belonging != null) {
                belonging.addInst(this);
            }
        }
    }

    public boolean isTerminalInst() {
        return false;
    }

    public abstract void accept(IRVisitor visitor);
}
