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
    public CallInst(String name, Block belonging, Function func, Entity... params) {
        super(((FuncType) func.type).retType, name, belonging);
        addOperand(func);
        for (var i : params) {
            addOperand(i);
        }
//        paramList.addAll(Arrays.asList(params));
    }

    public CallInst(String name, Block belonging, Function func, ArrayList<Entity> params) {
        super(((FuncType) func.type).retType, name, belonging);
        addOperand(func);
        for (var i : params) {
            addOperand(i);
        }
//        paramList.addAll(params);
    }

    public Function func() {
        return (Function) operands.get(0);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (!(this.type instanceof VoidType)) {
            str.append("%s = ".formatted(name()));
        }
        str.append("call %s %s(".formatted(type, func().name()));
        if (operands.size() > 1) {
            str.append(operands.get(1).nameWithType());
            for (int i = 2; i < operands.size(); ++i) {
                str.append(", ");
                str.append(operands.get(i).nameWithType());
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
