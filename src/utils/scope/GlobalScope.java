package src.utils.scope;

import src.ast.rootNode.FuncDefNode;
import src.ast.rootNode.ParamNode;
import src.ast.rootNode.TypeNode;
import src.ast.rootNode.UnitParamNode;
import src.utils.Position;
import src.utils.error.SemanticError;

import java.util.HashMap;

public class GlobalScope extends Scope {
    public HashMap<String, ClassScope> classes = new HashMap<>();

    public GlobalScope() {
        super(null);
    }

    public void initialize() {
        TypeNode VoidType = new TypeNode("void", false, null);
        TypeNode IntType = new TypeNode("int", false, null);
        TypeNode StringType = new TypeNode("string", true, null);

        var params = new ParamNode(null);
        params.lists.add(new UnitParamNode(StringType, "str", null));
        funcMembers.put("print", new FuncDefNode(VoidType, "print", params, null, null));
        funcMembers.put("println", new FuncDefNode(VoidType, "println", params, null, null));

        params = new ParamNode(null);
        params.lists.add(new UnitParamNode(IntType, "n", null));
        funcMembers.put("printInt", new FuncDefNode(VoidType, "printInt", params, null, null));
        funcMembers.put("printlnInt", new FuncDefNode(VoidType, "printlnInt", params, null, null));

        funcMembers.put("getString", new FuncDefNode(StringType, "getString", null, null, null));
        funcMembers.put("getInt", new FuncDefNode(IntType, "getInt", null, null, null));

        params = new ParamNode(null);
        params.lists.add(new UnitParamNode(IntType, "i", null));
        funcMembers.put("toString", new FuncDefNode(StringType, "toString", params, null, null));

        ClassScope stringClass = new ClassScope("string", this);
        stringClass.addFuncDef(new FuncDefNode(IntType, "length", null, null, null));
        params = new ParamNode(null);
        params.lists.add(new UnitParamNode(IntType, "left", null));
        params.lists.add(new UnitParamNode(IntType, "right", null));
        stringClass.addFuncDef(new FuncDefNode(StringType, "substring", params, null, null));
        stringClass.addFuncDef(new FuncDefNode(IntType, "parseInt", null, null, null));
        params = new ParamNode(null);
        params.lists.add(new UnitParamNode(IntType, "pos", null));
        stringClass.addFuncDef(new FuncDefNode(IntType, "ord", params, null, null));

        this.addClassDef(stringClass, null);

        ///funcMembers.put("size", new FuncDefNode(IntType, "size", null, null, null));
    }

    public void addClassDef(ClassScope classScope, Position pos) {
        if (classes.containsKey(classScope.className)) {
            throw new SemanticError(pos, "Duplicate class: '" + classScope.className + "'");
        }
        if (funcMembers.containsKey(classScope.className)) {
            throw new SemanticError(pos, "Class name '" + classScope.className + "' is the same as the global function name");
        }
        classes.put(classScope.className, classScope);
    }

    public ClassScope getClassScope(String name) {
        return classes.get(name);
    }

    public boolean hasClassScope(String name) {
        return classes.containsKey(name);
    }

    @Override
    public void addFuncDef(FuncDefNode func) {
        if (funcMembers.containsKey(func.funcName)) {
            throw new SemanticError(func.pos, "Multiple definition of func " + func.funcName);
        }
        if (classes.containsKey(func.funcName)) {
            throw new SemanticError(func.pos, "Same name as the class " + func.funcName);
        }
        funcMembers.put(func.funcName, func);
    }

    @Override
    public FuncDefNode getFuncDef(String name) {
        return funcMembers.get(name);
    }
}
