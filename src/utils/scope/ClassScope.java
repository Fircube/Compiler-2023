package src.utils.scope;

import src.ast.rootNode.ClassConNode;
import src.ast.rootNode.FuncDefNode;
import src.ast.stmtNode.UnitVarDefNode;
import src.utils.error.SemanticError;

public class ClassScope extends Scope {
    public String className;
    public ClassConNode con;

    public ClassScope(String className, GlobalScope parent) {
        super(parent);
        this.className = className;
    }

    @Override
    public void addVarDef(UnitVarDefNode var) {
        super.addVarDef(var);
    }

    @Override
    public UnitVarDefNode getVarDef(String name, boolean isLocal) {
        return super.getVarDef(name, isLocal);
    }

    @Override
    public void addFuncDef(FuncDefNode func) {
        if (funcMembers.containsKey(func.funcName)) {
            throw new SemanticError(func.pos, "'" + func.funcName + "' is already defined in Class " + className);
        }
        funcMembers.put(func.funcName, func);
    }

    @Override
    public FuncDefNode getFuncDef(String name) {
        return funcMembers.get(name);
    }
}
