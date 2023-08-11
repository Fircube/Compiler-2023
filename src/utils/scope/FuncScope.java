package src.utils.scope;

import src.utils.Type;

public class FuncScope extends Scope {
    public Type returnType;
    public boolean hasReturnStmt = false;

    public FuncScope(Type returnType, Scope parent) {
        super(parent);
        this.returnType = returnType;
    }

    public boolean inLoop() {
        return super.inLoop();
    }

    @Override
    public Type getRetType() {
        return returnType;
    }
}
