package src.utils.scope;

import src.ast.rootNode.ClassConNode;
import src.ast.rootNode.FuncDefNode;
import src.ast.stmtNode.UnitVarDefNode;
import src.ir.Function;
import src.utils.error.SemanticError;

import java.util.HashMap;

public class ClassScope extends Scope {
    public String className;
    public ClassConNode con;

    public HashMap<String, Function> functions = new HashMap<>();
    public HashMap<String, Integer> varIndex = new HashMap<>();

    public ClassScope(String className, GlobalScope parent) {
        super(parent);
        this.className = className;
    }

    @Override
    public void addVarDef(UnitVarDefNode var) {
        super.addVarDef(var);
        varIndex.put(var.name, varIndex.size());
    }

    @Override
    public UnitVarDefNode getVarDef(String name, boolean isLocal) {
        return super.getVarDef(name, isLocal);
    }

    public Integer getVarIdx(String name) {
        return varIndex.get(name);
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

    public void addFunction(String name, Function func) {
        functions.put(name, func);
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }
}
