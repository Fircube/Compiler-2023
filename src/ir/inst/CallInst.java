package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.Function;
import src.ir.IRVisitor;
import src.ir.type.FuncType;
import src.ir.type.VoidType;

import java.util.ArrayList;
import java.util.Arrays;

public class CallInst extends Inst {
    public Function func;
    public ArrayList<Entity> paramList = new ArrayList<>();

    public CallInst(String name, Block belonging, Function func, Entity... params) {
        super(((FuncType) func.type).retType, name, belonging);
        this.func = func;
        paramList.addAll(Arrays.asList(params));
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (!(this.type instanceof VoidType)) {
            str.append("%s = ".formatted(name()));
        }
        str.append("call %s %s(".formatted(type, func.name()));
        if (!paramList.isEmpty()) {
            str.append(paramList.get(0).nameWithType());
            for (int i = 1; i < paramList.size(); ++i) {
                str.append(", ");
                str.append(paramList.get(i).nameWithType());
            }
        }
        str.append(")");
        return str.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
