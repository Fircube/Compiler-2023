package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.IntType;

public class MvInst extends Inst{
    public MvInst(Entity dest, Entity src, Block belonging){
        super(null, null, belonging);
        addOperand(dest);
        addOperand(src);
    }

    public Entity dest(){
        return operands.get(0);
    }

    public Entity src(){
        return operands.get(1);
    }

    @Override
    public String toString() {
        return "move %s %s".formatted(dest().name(),src().name());
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

}
