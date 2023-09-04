package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.VoidType;

public class BrInst extends Inst {
    public boolean isJump;
    public int phi=0;
    public Entity con;
    public Block trueDest;
    public Block falseDest;

    public BrInst(Block dest, Block belonging) {
        super(new VoidType(), "br", belonging);
        this.isJump = true;
        this.trueDest = dest;
    }

    public BrInst(int phi,Block dest, Block belonging) {
        super(new VoidType(), "br", belonging);
        this.phi= phi;
        this.isJump = true;
        this.trueDest = dest;
    }

    public BrInst(Entity con, Block trueDest, Block falseDest, Block belonging) {
        super(new VoidType(), "br", belonging);
        this.isJump = false;
        this.con = con;
        this.trueDest = trueDest;
        this.falseDest = falseDest;
    }

    public boolean isTerminalInst() {
        return true;
    }

    @Override
    public String toString() {
        if (isJump) {
            return "br %s".formatted(trueDest.nameWithType());
        } else {
            return "br %s, %s, %s".formatted(con.nameWithType(), trueDest.nameWithType(), falseDest.nameWithType());
        }
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
