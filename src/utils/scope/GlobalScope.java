package src.utils.scope;

import src.asm.ASMFunction;
import src.asm.operand.GlobalStr;
import src.asm.operand.GlobalVar;
import src.ast.rootNode.FuncDefNode;
import src.ast.rootNode.ParamNode;
import src.ast.rootNode.TypeNode;
import src.ast.rootNode.UnitParamNode;
import src.ir.Entity;
import src.ir.Function;
import src.ir.constant.GlobalVariable;
import src.ir.constant.StringConst;
import src.ir.type.*;
import src.utils.Position;
import src.utils.error.SemanticError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GlobalScope extends Scope {
    public HashMap<String, ClassScope> classes = new HashMap<>();

    public HashMap<String, GlobalVariable> globalVars = new HashMap<>();
    public HashMap<String, ClassType> classTypes = new HashMap<>();
    public HashMap<String, Function> functions = new HashMap<>();
    public HashMap<String, Function> builtinFunc = new HashMap<>();
    public HashMap<String, StringConst> stringConst = new HashMap<>();

    public ArrayList<GlobalVar> gVars = new ArrayList<>();
    public ArrayList<GlobalStr> gStrs = new ArrayList<>();
    public ArrayList<ASMFunction> funcs = new ArrayList<>();

    public int phiCnt = 1;

    public GlobalScope() {
        super(null);
    }

    public void initAST() {
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

    }

    public void initIR() {
        BaseType VoidType = new VoidType();
        BaseType BoolType = new IntType(1);
        BaseType i32Type = new IntType(32);
        BaseType i8PtrType = new PtrType(new IntType(8));
        addGlobalIRFunc("print", false, VoidType, i8PtrType);
        addGlobalIRFunc("println", false, VoidType, i8PtrType);
        addGlobalIRFunc("printInt", false, VoidType, i32Type);
        addGlobalIRFunc("printlnInt", false, VoidType, i32Type);
        addGlobalIRFunc("getString", false, i8PtrType);
        addGlobalIRFunc("getInt", false, i32Type);
        addGlobalIRFunc("toString", false, i8PtrType, i32Type);
        addGlobalIRFunc("_malloc", false, i8PtrType, i32Type);

        addGlobalIRFunc("_str_length", true, i32Type, i8PtrType);
        addGlobalIRFunc("_str_substring", true, i8PtrType, i8PtrType, i32Type, i32Type);
        addGlobalIRFunc("_str_parseInt", true, i32Type, i8PtrType);
        addGlobalIRFunc("_str_ord", true, i32Type, i8PtrType, i32Type);
        addGlobalIRFunc("_str_add", false, i8PtrType, i8PtrType, i8PtrType);

        addGlobalIRFunc("_str_eq", false, BoolType, i8PtrType, i8PtrType);
        addGlobalIRFunc("_str_ne", false, BoolType, i8PtrType, i8PtrType);
        addGlobalIRFunc("_str_sgt", false, BoolType, i8PtrType, i8PtrType);
        addGlobalIRFunc("_str_sge", false, BoolType, i8PtrType, i8PtrType);
        addGlobalIRFunc("_str_slt", false, BoolType, i8PtrType, i8PtrType);
        addGlobalIRFunc("_str_sle", false, BoolType, i8PtrType, i8PtrType);
    }

    private void addGlobalIRFunc(String funcName, boolean isMember, BaseType returnType, BaseType... paramTypes) {
        FuncType funcType = new FuncType(returnType);
        funcType.paramTypes.addAll(Arrays.asList(paramTypes));
        Function function = new Function(funcType, "@" + funcName, isMember);
        this.builtinFunc.put(funcName, function);
    }

    public void addClassDef(ClassScope classScope, Position pos) {
        if (classes.containsKey(classScope.className)) {
            throw new SemanticError(pos, "Multiple definition of class: '" + classScope.className + "'");
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

    @Override
    public Entity getVar(String name, boolean recursive) {
        return globalVars.get(name);
    }

    public void addClassType(String name, ClassType cls) {
        classTypes.put(name, cls);
    }

    public ClassType getClassType(String name) {
        return classTypes.get(name);
    }

    public void addFunction(String name, Function func) {
        functions.put(name, func);
    }

    public Function getFunction(String name) {
        if (functions.containsKey(name)) return functions.get(name);
        return builtinFunc.get(name);
    }

}
