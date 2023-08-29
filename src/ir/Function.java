package src.ir;

import src.ir.constant.Const;
import src.ir.inst.Inst;
import src.ir.type.FuncType;

import java.util.ArrayList;

public class Function extends Const {
    public ArrayList<Block> blocks = new ArrayList<>();
    public ArrayList<Entity> params = new ArrayList<>();
    public Block entryBlock;
    public Entity retValPtr;
    public Inst exit;
    public boolean isMember;

    public Function(FuncType type, String name, boolean isMember) {
        super(type, name);
        this.isMember = isMember;
    }

    public void addParams(Entity param) {
        params.add(param);
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
