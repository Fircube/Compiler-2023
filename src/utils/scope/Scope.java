package src.utils.scope;

import src.ast.rootNode.FuncDefNode;
import src.ast.stmtNode.UnitVarDefNode;
import src.ir.Entity;
import src.ir.inst.AllocaInst;
import src.utils.Type;
import src.utils.error.SemanticError;

import java.util.HashMap;

public class Scope {
    private final Scope parentScope;
    public HashMap<String, UnitVarDefNode> varMembers = new HashMap<>();
    public HashMap<String, FuncDefNode> funcMembers = new HashMap<>();

    public HashMap<String, AllocaInst> vars = new HashMap<>();

    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public boolean isInLoop() {
        if (this instanceof LoopScope) return true;
        if (!(this instanceof FuncScope) && !(this instanceof ClassScope) && this.parentScope != null)
            return parentScope.isInLoop();
        return false;
    }

    public LoopScope inLoop() {
        if (this instanceof LoopScope) return (LoopScope) this;
        if (parentScope != null) return parentScope.inLoop();
        return null;
    }

    public ClassScope inClass() {
        if (this instanceof ClassScope) return (ClassScope) this;
        if (parentScope != null) return parentScope.inClass();
        return null;
    }

    public FuncScope inFunc() {
        if (this instanceof FuncScope) return (FuncScope) this;
        if (parentScope != null) return parentScope.inFunc();
        return null;
    }


    public void addVarDef(UnitVarDefNode var) {
        if (varMembers.containsKey(var.name)) {
            throw new SemanticError(var.pos, "Variable '" + var.name + "' is already defined in the scope");
        }
        varMembers.put(var.name, var);
    }

    public UnitVarDefNode getVarDef(String name, boolean inLocal) {
        if (inLocal) return varMembers.get(name);
        if (varMembers.containsKey(name)) return varMembers.get(name);
        if (parentScope != null) return parentScope.getVarDef(name, false);
        return null;
    }

    public void addFuncDef(FuncDefNode func) {
    }

    public FuncDefNode getFuncDef(String name) {
        return null;
    }

    public Type getRetType() {
        return parentScope.getRetType();
    }

    public void addVars(String name, AllocaInst var) {
        vars.put(name, var);
    }

    public Entity getVar(String name, boolean inLocal) {
        if (inLocal) return vars.get(name);
        if (vars.containsKey(name)) return vars.get(name);
        if (parentScope != null) return parentScope.getVar(name, false);
        return null;
    }
}
