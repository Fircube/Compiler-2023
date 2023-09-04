package src.ir;

import src.ast.ASTVisitor;
import src.ast.exprNode.*;
import src.ast.rootNode.*;
import src.ast.stmtNode.*;
import src.ir.constant.*;
import src.ir.inst.*;
import src.ir.type.*;
import src.utils.Position;
import src.utils.Type;
import src.utils.error.IRError;
import src.utils.scope.*;

import java.util.ArrayList;
import java.util.HashMap;

public class IRBuilder implements ASTVisitor {
    private final GlobalScope globalScope;

    private int phiCnt = 1;
    private Scope curScope;
    private Block curBlock;
    private Function curFunc;

    private String curClsName;
    private ClassScope curClsScope;
    private ClassType curClsType;


    public IRBuilder(GlobalScope globalScope) {
        this.curScope = this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode it) {
        // init class
        for (var i : it.defs) {
            if (i instanceof ClassDefNode cls) {
                ClassType clsType = new ClassType("%class." + cls.className, 0);
                globalScope.addClassType(cls.className, clsType);
                curScope = globalScope.getClassScope(cls.className);
                for (var vars : cls.members) {
                    for (var v : vars.defs) {
                        v.type.accept(this);
                        clsType.memberTypes.add(v.type.irType);
                    }
                }
                clsType.size = clsType.memberTypes.size() << 2;

                for (var func : cls.func) {
                    func.returnType.accept(this);
                    FuncType funcType = new FuncType(func.returnType.irType);
                    funcType.paramTypes.add(new PtrType(clsType)); // add this pointer
                    for (var p : func.parameters.lists) {
                        p.type.accept(this);
                        funcType.paramTypes.add(p.type.irType);
                    }
                    String funcName = "@%s.%s".formatted(cls.className, func.funcName);
                    Function function = new Function(funcType, funcName, true);
                    ((ClassScope) curScope).functions.put(func.funcName, function);
                    globalScope.addFunction(funcName, function);
                }
                FuncType funcType = new FuncType(new VoidType());
                funcType.paramTypes.add(new PtrType(clsType)); // add this pointer
                String funcName = "@%s.%s".formatted(cls.className, cls.className);
                Function function = new Function(funcType, funcName, true);
                ((ClassScope) curScope).functions.put(cls.className, function);
                globalScope.addFunction(funcName, function);
            }
            // declare global variable
            if (i instanceof VarDefNode vars) {
                for (var j : vars.defs) {
                    j.type.accept(this);
                    globalScope.globalVars.put(j.name, new GlobalVariable(j.type.irType, rename("@" + j.name)));
                }
            }
            // declare global function
            if (i instanceof FuncDefNode func) {
                func.returnType.accept(this);
                FuncType funcType = new FuncType(func.returnType.irType);
                for (var p : func.parameters.lists) {
                    p.type.accept(this);
                    funcType.paramTypes.add(p.type.irType);
                }
                String funcName;
                funcName = "@%s".formatted(func.funcName);
                Function function = new Function(funcType, funcName, false);
                globalScope.addFunction(func.funcName, function);
            }
        }

        // init global variables
        // In order to keep all init functions in one function, initialization is operated at root
        FuncType funcType = new FuncType(new VoidType());
        String funcName = "@global_variable_init";
        curFunc = new Function(funcType, funcName, false);
        globalScope.addFunction("global_var_init", curFunc);
        curBlock = curFunc.entryBlock = new Block(rename("entry"), curFunc);
        curFunc.exit = new RetInst(null); // return void
        for (var i : it.defs) {
            if (i instanceof VarDefNode vars) {
                for (var j : vars.defs) {
                    GlobalVariable v = (GlobalVariable) globalScope.getVar(j.name, false);
                    if (j.expr == null) continue;
                    j.expr.accept(this);
                    if (j.expr.val instanceof Const) v.init = j.expr.val;
                    else new StoreInst(getValue(j.expr), v, curBlock);
                }
            }
        }
        if (!curBlock.terminated) curFunc.exit.addToBlock(curBlock);

        curScope = globalScope;
        curBlock = null;
        curFunc = null;

        for (var i : it.defs) {
            if (i instanceof ClassDefNode) {
                i.accept(this);
            }
        }
        for (var i : it.defs) {
            if (i instanceof FuncDefNode) {
                i.accept(this);
            }
        }
    }

    @Override
    public void visit(ClassConNode it) {
        curFunc = curClsScope.functions.get(curClsName);
        curFunc.exit = new RetInst(null);
        curBlock = curFunc.entryBlock = new Block(rename("entry"), curFunc);

        addThis();

        it.suite.accept(this);
        if (!curBlock.terminated) curFunc.exit.addToBlock(curBlock);
        curScope = curScope.getParentScope();
    }

    private void addThis() {
        PtrType thisPtr = new PtrType(curClsType);
        Entity value = new Entity(thisPtr, rename("%this"));
        curFunc.addParams(value);
        AllocaInst ptr = new AllocaInst(thisPtr, rename("%this.addr"), curBlock);
        curScope.addVars("this", ptr);
        new StoreInst(value, ptr, curBlock);
    }

    @Override
    public void visit(ClassDefNode it) {
        curClsName = it.className;
        curClsScope = globalScope.getClassScope(curClsName);
        curClsType = globalScope.getClassType(curClsName);
        curScope = curClsScope;
        if (it.con != null) {
            it.con.accept(this);
        } else {
            curFunc = curClsScope.getFunction(curClsName);
            curBlock = curFunc.entryBlock = new Block(rename("entry"), curFunc);
            PtrType thisPtr = new PtrType(curClsType);
            curFunc.addParams(new Entity(thisPtr, rename("%this")));
            new RetInst(curBlock);
        }
        for (var i : it.func) {
            i.accept(this);
        }

        curScope = curScope.getParentScope();
        curClsName = null;
        curClsScope = null;
        curClsType = null;
    }

    @Override
    public void visit(FuncDefNode it) {
        if (curClsScope != null) curFunc = curClsScope.getFunction(it.funcName);
        else curFunc = globalScope.getFunction(it.funcName);
        curScope = new FuncScope(it.returnType.type, curScope);
        curBlock = curFunc.entryBlock = new Block(rename("entry"), curFunc);
        if (it.funcName.equals("main")) {
            new CallInst("%main.call", curBlock, globalScope.getFunction("global_var_init"));
        }
        FuncType funcType = (FuncType) curFunc.type;
        if (funcType.retType instanceof VoidType) {
            curFunc.exit = new RetInst(null);
        } else {
            curFunc.retValPtr = new AllocaInst(funcType.retType, rename("%retval.addr"), curBlock);
            curScope.addVars("retval", (AllocaInst) curFunc.retValPtr);
            curFunc.exit = new RetInst(new LoadInst(rename("%retval"), curFunc.retValPtr, null), null);
        }
        int idx = 0;
        if (curFunc.isMember) { // add "this" pointer
            idx = 1;
            addThis();
        }
        for (int i = 0; i < it.parameters.lists.size(); ++i) {
            UnitParamNode param = it.parameters.lists.get(i);
            BaseType type = funcType.paramTypes.get(i + idx);
            Entity value = new Entity(type, rename("%" + param.paramName));
            curFunc.addParams(value);
            AllocaInst ptr = new AllocaInst(type, rename("%" + param.paramName + ".addr"), curBlock);
            curScope.addVars(param.paramName, ptr);
            new StoreInst(value, ptr, curBlock);
        }

        it.suite.accept(this);
        if (!curBlock.terminated) {
            if (((RetInst) curFunc.exit).value != null)
                ((LoadInst) ((RetInst) curFunc.exit).value).addToBlock(curBlock);
            curFunc.exit.addToBlock(curBlock);
        }
        curScope = curScope.getParentScope();
    }

    @Override
    public void visit(ParamNode it) {
    }

    @Override
    public void visit(TypeNode it) {
        it.irType = convertType(it.type);
    }

    @Override
    public void visit(UnitParamNode it) {
    }

    @Override
    public void visit(BlockNode it) {
        curScope = new Scope(curScope);
        for (var i : it.stmts) i.accept(this);
        curScope = curScope.getParentScope();
    }

    @Override
    public void visit(BreakStmtNode it) {
        new BrInst(curScope.inLoop().breakBlock, curBlock);
    }

    @Override
    public void visit(ContinueStmtNode it) {
        new BrInst(curScope.inLoop().continueBlock, curBlock);
    }

    @Override
    public void visit(ExprStmtNode it) {
        if (it.expr != null) it.expr.accept(this);
    }

    @Override
    public void visit(ForStmtNode it) {
        Block conBlock = new Block(rename("for.con"), curFunc);
        Block stepBlock = new Block(rename("for.step"), curFunc);
        Block bodyBlock = new Block(rename("for.body"), curFunc);
        Block endBlock = new Block(rename("for.end"), curFunc);

        curScope = new LoopScope(curScope);
        ((LoopScope) curScope).breakBlock = endBlock;
        ((LoopScope) curScope).continueBlock = stepBlock;
        if (it.varInit != null) it.varInit.accept(this);
        if (it.forInit != null) it.forInit.accept(this);
        new BrInst(conBlock, curBlock);

        curBlock = conBlock;
        if (it.forCon != null) {
            it.forCon.accept(this);
            new BrInst(getValue(it.forCon), bodyBlock, endBlock, curBlock);
        } else {
            new BrInst(bodyBlock, curBlock);
        }

        curBlock = bodyBlock;
        it.body.accept(this);
        new BrInst(stepBlock, curBlock);

        curBlock = stepBlock;
        if (it.forStep != null) {
            it.forStep.accept(this);
        }
        new BrInst(conBlock, curBlock);

        curBlock = endBlock;
        curScope = curScope.getParentScope();
    }

    @Override
    public void visit(IfStmtNode it) {
        it.condition.accept(this);
        Block thenBlock = new Block(rename("if.then"), curFunc);
        Block elseBlock = new Block(rename("if.else"), curFunc);
        Block endBlock = new Block(rename("if.end"), curFunc);

        new BrInst(getValue(it.condition), thenBlock, elseBlock, curBlock);

        curScope = new Scope(curScope);
        curBlock = thenBlock;
        it.thenStmt.accept(this);
        new BrInst(endBlock, curBlock);
        curScope = curScope.getParentScope();

        curBlock = elseBlock;
        if (it.elseStmt != null) {
            curScope = new Scope(curScope);
            it.elseStmt.accept(this);
            curScope = curScope.getParentScope();
        }
        new BrInst(endBlock, curBlock);

        curBlock = endBlock;
    }

    @Override
    public void visit(ReturnStmtNode it) {
        if (it.ret != null) {
            it.ret.accept(this);
            new StoreInst(getValue(it.ret), curFunc.retValPtr, curBlock);
            new RetInst(getValue(it.ret), curBlock);
        } else new RetInst(curBlock);
    }


    @Override
    public void visit(UnitVarDefNode it) {
        it.type.accept(this);
        var ptr = new AllocaInst(it.type.irType, rename("%" + it.name + ".addr"), curBlock);
        if (it.expr != null) {
            it.expr.accept(this);
            new StoreInst(getValue(it.expr), ptr, curBlock);
        }
        curScope.addVars(it.name, ptr);
    }

    @Override
    public void visit(VarDefNode it) {
        for (var i : it.defs) i.accept(this);
    }

    @Override
    public void visit(WhileStmtNode it) {
        Block conBlock = new Block(rename("while.con"), curFunc);
        Block bodyBlock = new Block(rename("while.body"), curFunc);
        Block endBlock = new Block(rename("while.end"), curFunc);

        curScope = new LoopScope(curScope);
        ((LoopScope) curScope).breakBlock = endBlock;
        ((LoopScope) curScope).continueBlock = conBlock;
        new BrInst(conBlock, curBlock);

        curBlock = conBlock;
        it.condition.accept(this);
        new BrInst(getValue(it.condition), bodyBlock, endBlock, curBlock);

        curBlock = bodyBlock;
        it.body.accept(this);
        new BrInst(conBlock, curBlock);

        curBlock = endBlock;
        curScope = curScope.getParentScope();
    }

    @Override
    public void visit(ArrayExprNode it) {
        it.arrayName.accept(this);
        Entity array = getValue(it.arrayName);
        it.index.accept(this);
        it.ptr = new GetElementPtrInst(array.type, rename("%array"), curBlock, array, getValue(it.index));
    }

    @Override
    public void visit(AssignExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        new StoreInst(getValue(it.rhs), it.lhs.ptr, curBlock);
    }

    @Override
    public void visit(BinaryExprNode it) {
        it.lhs.accept(this);
        if (it.op.equals("&&")) {
            if (getValue(it.lhs) instanceof IntConst b) {
                if (b.value == 0) {
                    it.val = new BoolConst(false);
                } else {
                    it.rhs.accept(this);
                    it.val = it.rhs.val;
                }
                return;
            }
            it.ptr = new AllocaInst(new IntType(1), rename("%and.result.addr"), curBlock);
            curScope.addVars("ans.result", (AllocaInst) it.ptr);
            Block rhsBlock = new Block(rename("and.rhs"), curFunc);
            Block skipBlock = new Block(rename("and.skip"), curFunc);
            Block endBlock = new Block(rename("and.end"), curFunc);
            new BrInst(getValue(it.lhs), rhsBlock, skipBlock, curBlock);

            curBlock = rhsBlock;
            it.rhs.accept(this);
            new StoreInst(getValue(it.rhs), it.ptr, curBlock);
            new BrInst(endBlock, curBlock);

            curBlock = skipBlock;
            new StoreInst(new BoolConst(false), it.ptr, curBlock);
            new BrInst(endBlock, curBlock);

            curBlock = endBlock;
            return;
        } else if (it.op.equals("||")) {
            if (getValue(it.lhs) instanceof IntConst b) {
                if (b.value == 0) {
                    it.rhs.accept(this);
                    it.val = it.rhs.val;
                } else {
                    it.val = new BoolConst(true);
                }
                return;
            }
            it.ptr = new AllocaInst(new IntType(1), rename("%or.result.addr"), curBlock);
            curScope.addVars("or.result", (AllocaInst) it.ptr);
            Block rhsBlock = new Block(rename("or.rhs"), curFunc);
            Block skipBlock = new Block(rename("or.skip"), curFunc);
            Block endBlock = new Block(rename("or.end"), curFunc);
            new BrInst(getValue(it.lhs), skipBlock, rhsBlock, curBlock);

            curBlock = skipBlock;
            new StoreInst(new BoolConst(true), it.ptr, curBlock);
            new BrInst(endBlock, curBlock);

            curBlock = rhsBlock;
            it.rhs.accept(this);
            new StoreInst(getValue(it.rhs), it.ptr, curBlock);
            new BrInst(endBlock, curBlock);

            curBlock = endBlock;
            return;
        }
        it.rhs.accept(this);
        if (it.lhs.type.isString()) {
            it.val = new CallInst(tempName(), curBlock, getStrFunc(it.op), getValue(it.lhs), getValue(it.rhs));
            return;
        }
        if (it.lhs.val instanceof IntConst l && it.rhs.val instanceof IntConst r) {
            if ((it.op.equals("/") || it.op.equals("%")) && r.value == 0)
                throw new IRError(it.pos, "The divisor cannot be 0");
            switch (it.op) {
                case "+" -> it.val = new IntConst(l.value + r.value);
                case "-" -> it.val = new IntConst(l.value - r.value);
                case "*" -> it.val = new IntConst(l.value * r.value);
                case "/" -> it.val = new IntConst(l.value / r.value);
                case "%" -> it.val = new IntConst(l.value % r.value);
                case ">" -> it.val = new BoolConst(l.value > r.value);
                case "<" -> it.val = new BoolConst(l.value < r.value);
                case ">=" -> it.val = new BoolConst(l.value >= r.value);
                case "<=" -> it.val = new BoolConst(l.value <= r.value);
                case "!=" -> it.val = new BoolConst(l.value != r.value);
                case "==" -> it.val = new BoolConst(l.value == r.value);
                case ">>" -> it.val = new IntConst(l.value >> r.value);
                case "<<" -> it.val = new IntConst(l.value << r.value);
                case "&" -> it.val = new IntConst(l.value & r.value);
                case "|" -> it.val = new IntConst(l.value | r.value);
                case "^" -> it.val = new IntConst(l.value ^ r.value);
                default -> throw new IRError(it.pos, "unknown operator");
            }
            return;
        }

        String name = null;
        switch (it.op) {
            case ">" -> name = "sgt";
            case "<" -> name = "slt";
            case ">=" -> name = "sge";
            case "<=" -> name = "sle";
            case "!=" -> name = "ne";
            case "==" -> name = "eq";
        }
        if (name != null) {
            it.val = new IcmpInst(it.op, getValue(it.lhs), getValue(it.rhs), rename("%" + name + ".result"), curBlock);
            return;
        }
        switch (it.op) {
            case "+" -> name = "add";
            case "-" -> name = "sub";
            case "*" -> name = "mul";
            case "/" -> name = "div";
            case "%" -> name = "mod";
            case ">>" -> name = "rshift";
            case "<<" -> name = "lshift";
            case "&" -> name = "bitand";
            case "|" -> name = "bitor";
            case "^" -> name = "bitxor";
            default -> throw new IRError(it.pos, "unknown operator");
        }
        it.val = new BinaryInst(it.op, getValue(it.lhs), getValue(it.rhs), rename("%" + name + ".result"), curBlock);
    }

    @Override
    public void visit(FuncCallExprNode it) {
        it.funcName.accept(this);
        Function function = (Function) it.funcName.val;
        ArrayList<Entity> params = new ArrayList<>();
        int idx = 0;
        if (function.isMember) { // add "this" pointer
            idx = 1;
            params.add(new LoadInst(rename("%this"), curScope.getVar("this", false), curBlock));
        }
        for (int i = 0; i < it.realParams.size(); ++i) {
            var param = it.realParams.get(i);
            param.accept(this);
            Entity val = getValue(param);
            val.type = ((FuncType) function.type).paramTypes.get(i + idx);
            params.add(val);
        }
        if (((FuncType) function.type).retType instanceof VoidType)
            it.val = new CallInst(rename("voidCall"), curBlock, function, params);
        else it.val = new CallInst(tempName(), curBlock, function, params);
    }

    @Override
    public void visit(IdentifierNode it) {
        if (it.isFunc) {
            if (curClsScope == null) it.val = globalScope.getFunction(it.name);
            if (it.val == null) it.val = curClsScope.getFunction(it.name);
            if (it.val == null) it.val = globalScope.getFunction(it.name);
        } else {
            it.ptr = curScope.getVar(it.name, false);
            if (it.ptr == null) { // member
                Entity thisPtr = new LoadInst(rename("%this"), curScope.getVar("this", false), curBlock);
                Integer index = curClsScope.getVarIdx(it.name);
                it.ptr = new GetElementPtrInst(new PtrType(curClsType.memberTypes.get(index)), rename("%" + it.name), curBlock, thisPtr, new IntConst(0), new IntConst(index));
            }
        }
    }

    @Override
    public void visit(LiteralNode it) {
        if (it.content.equals("this")) {
            it.ptr = curScope.getVar("this", false);
        } else {
            switch (it.type.typeName) {
                case "int" -> it.val = new IntConst(Integer.parseInt(it.content));
                case "bool" -> it.val = it.content.equals("true") ? new BoolConst(true) : new BoolConst(false);
                case "null" -> it.val = new NullConst();
                case "string" -> {
                    StringConst s;
                    if (globalScope.stringConst.containsKey(it.content)) s = globalScope.stringConst.get(it.content);
                    else {
                        s = new StringConst(rename("@str"), toStr(it.content));
                        globalScope.stringConst.put(it.content, s);
                    }
                    it.val = new GetElementPtrInst(new PtrType(new IntType(8)), tempName(), curBlock, s, new IntConst(0), new IntConst(0));
                }
                default -> throw new IRError(it.pos, "unknown literal");
            }
        }
    }

    @Override
    public void visit(MemberExprNode it) {
        it.className.accept(this);
        if (it.className.type.isArray) {  // .size
            var arrayPtr = new BitCastInst(tempName(), new PtrType(new IntType(32)), getValue(it.className), curBlock);
            var sizePtr = new GetElementPtrInst(new PtrType(new IntType(32)), tempName(), curBlock, arrayPtr, new IntConst(-1));
            it.val = new LoadInst(rename("%array.size"), sizePtr, curBlock);
        } else if (it.className.type.isString()) {
            Function function = globalScope.getFunction("_str_" + ((IdentifierNode) ((FuncCallExprNode) it.member).funcName).name);
            setFunc(it, function);
        } else {
            String className = it.className.type.typeName;
            ClassScope classScope = globalScope.getClassScope(className);
            ClassType classType = globalScope.getClassType(className);
            if (it.member.isFunc) {
                String funcName = ((IdentifierNode) ((FuncCallExprNode) it.member).funcName).name;
                Function function = classScope.getFunction(funcName);
                setFunc(it, function);
            } else {
                Integer index = classScope.getVarIdx(((IdentifierNode) it.member).name);
                it.ptr = new GetElementPtrInst(new PtrType(classType.memberTypes.get(index)), rename("%" + ((IdentifierNode) it.member).name), curBlock, getValue(it.className), new IntConst(0), new IntConst(index));
            }
        }
    }

    private void setFunc(MemberExprNode it, Function function) {
        ArrayList<Entity> params = new ArrayList<>();

        params.add(getValue(it.className));

        for (int i = 0; i < ((FuncCallExprNode) it.member).realParams.size(); ++i) {
            var param = ((FuncCallExprNode) it.member).realParams.get(i);
            param.accept(this);
            Entity val = getValue(param);
            val.type = ((FuncType) function.type).paramTypes.get(i + 1);
            params.add(val);
        }

        if (((FuncType) function.type).retType instanceof VoidType)
            it.val = it.member.val = new CallInst(rename("voidCall"), curBlock, function, params);
        else it.val = it.member.val = new CallInst(tempName(), curBlock, function, params);
    }


    @Override
    public void visit(NewExprNode it) {
        if (it.type.isArray) {
            var sizes = new ArrayList<Entity>();
            for (var i : it.sizeParams) {
                i.accept(this);
                sizes.add(getValue(i));
            }
            if (sizes.isEmpty()) it.val = new NullConst();
            else it.val = newArray(convertType(it.type), 0, sizes);
        } else {
            String className = it.type.typeName;
            ClassScope classScope = globalScope.getClassScope(className);
            ClassType classType = globalScope.getClassType(className);

            Function malloc = globalScope.getFunction("_malloc");
            var ptr = new CallInst(rename("%new.ptr"), curBlock, malloc, new IntConst(classType.size));
            it.val = new BitCastInst(rename("%new.clsPtr"), new PtrType(classType), ptr, curBlock);

            // constructor
            new CallInst(rename("%new." + className + ".con"), curBlock, classScope.getFunction(className), it.val);
        }
    }

    private Entity newArray(BaseType type, int idx, ArrayList<Entity> sizes) {
        Function malloc = globalScope.getFunction("_malloc");
        BaseType baseType = ((PtrType) type).baseType;
        Entity num = sizes.get(idx);
        var size = new BinaryInst("*", num, new IntConst(baseType.size), tempName(), curBlock);
        var mallocSize = new BinaryInst("+", size, new IntConst(4), rename("%new.mallocsize"), curBlock);

        var oriPtr = new CallInst(rename("%new.oriptr"), curBlock, malloc, mallocSize);
        var sizePtr = new BitCastInst(rename("%new.sizeptr"), new PtrType(new IntType(32)), oriPtr, curBlock);
        new StoreInst(num, sizePtr, curBlock);

        var tmpPtr = new GetElementPtrInst(new PtrType(new IntType(8)), tempName(), curBlock, oriPtr, new IntConst(4));
        var arrPtr = new BitCastInst(rename("%new.arrptr"), type, tmpPtr, curBlock);

        if (idx + 1 < sizes.size()) {
            var ptr = new AllocaInst(type, rename("%new.ptr"), curBlock);
            new StoreInst(arrPtr, ptr, curBlock);

            var endPtr = new GetElementPtrInst(type, rename("%new.endPtr"), curBlock, arrPtr, num);

            Block condBlock = new Block(rename("new.cond"), curFunc);
            Block bodyBlock = new Block(rename("new.body"), curFunc);
            Block endBlock = new Block(rename("new.end"), curFunc);

            new BrInst(condBlock, curBlock);

            curBlock = condBlock;
            var ptr1 = new LoadInst(rename("%new.ptr"), ptr, curBlock);
            var cond = new IcmpInst("!=", ptr1, endPtr, rename("%new.cmp"), curBlock);
            new BrInst(cond, bodyBlock, endBlock, curBlock);

            curBlock = bodyBlock;
            var ptr2 = new LoadInst(rename("%new.ptr"), ptr, curBlock);
            new StoreInst(newArray(baseType, idx + 1, sizes), ptr2, curBlock);
            new StoreInst(new GetElementPtrInst(type, tempName(), curBlock, ptr2, new IntConst(1)), ptr, curBlock);
            new BrInst(condBlock, curBlock);

            curBlock = endBlock;
        }
        return arrPtr;
    }

    @Override
    public void visit(ParenExprNode it) {
        it.expr.accept(this);
        it.val = it.expr.val;
        it.ptr = it.expr.ptr;
    }

    @Override
    public void visit(PostExprNode it) {
        it.expr.accept(this);
        it.val = getValue(it.expr);
        Entity value;
        switch (it.op) {
            case "++" -> value = new BinaryInst("+", it.val, new IntConst(1), rename("%inc"), curBlock);
            case "--" -> value = new BinaryInst("-", it.val, new IntConst(1), rename("%dec"), curBlock);
            default -> value = null;
        }
        new StoreInst(value, it.expr.ptr, curBlock);
    }

    @Override
    public void visit(PreExprNode it) {
        it.expr.accept(this);
        it.ptr = it.expr.ptr;
        switch (it.op) {
            case "++" -> it.val = new BinaryInst("+", getValue(it.expr), new IntConst(1), rename("%inc"), curBlock);
            case "--" -> it.val = new BinaryInst("-", getValue(it.expr), new IntConst(1), rename("%dec"), curBlock);
            default -> it.val = null;
        }
        new StoreInst(it.val, it.expr.ptr, curBlock);
    }

    @Override
    public void visit(TernaryExprNode it) {
        it.condition.accept(this);
        Block trueBlock = new Block(rename("ter.true"), curFunc);
        Block falseBlock = new Block(rename("ter.false"), curFunc);
        Block endBlock = new Block(rename("ter.end"), curFunc);
        Block trueSource, falseSource;
        new BrInst(getValue(it.condition), trueBlock, falseBlock, curBlock);

        curBlock = trueBlock;
        it.trueExpr.accept(this);
        trueSource = curBlock;
        new BrInst(phiCnt, endBlock, curBlock);
        curBlock = falseBlock;
        it.falseExpr.accept(this);
        falseSource = curBlock;
        new BrInst(phiCnt, endBlock, curBlock);
        curBlock = endBlock;
        if (it.trueExpr.val != null || it.trueExpr.ptr != null) {
            if (!(getValue(it.trueExpr).type instanceof VoidType)) {
                it.val = new PhiInst(phiCnt, getValue(it.trueExpr).type, rename("%ter.result"), curBlock);
                ((PhiInst) it.val).addBranch(getValue(it.trueExpr), trueSource);
                ((PhiInst) it.val).addBranch(getValue(it.falseExpr), falseSource);
                phiCnt++;
            }
        }
    }

    @Override
    public void visit(UnaryExprNode it) {
        it.expr.accept(this);
        if (it.expr.val instanceof Const) {
            if (it.expr.val instanceof BoolConst) {
                if (it.op.equals("!")) it.val = new IntConst(((BoolConst) it.expr.val).value ? 0 : 1);
            } else if (it.expr.val instanceof IntConst) {
                switch (it.op) {
                    case "+" -> it.val = new IntConst(((IntConst) it.expr.val).value);
                    case "-" -> it.val = new IntConst(-(((IntConst) it.expr.val).value));
                    case "!" -> it.val = new IntConst(((IntConst) it.expr.val).value == 0 ? 1 : 0);
                    case "~" -> it.val = new IntConst(~(((IntConst) it.expr.val).value));
                    default -> it.val = null;
                }
            }
            return;
        }
        switch (it.op) {
            case "+" -> it.val = getValue(it.expr);
            case "-" -> it.val = new BinaryInst("-", new IntConst(0), getValue(it.expr), rename("%sub"), curBlock);
            case "!" -> it.val = new BinaryInst("^", getValue(it.expr), new BoolConst(true), rename("%not"), curBlock);
            case "~" -> it.val = new BinaryInst("^", getValue(it.expr), new IntConst(-1), rename("%bitNot"), curBlock);
            default -> it.val = null;
        }

    }

    // convert from Type to BaseType
    private BaseType convertType(Type type) {
        if (type.isArray) {
            Type upper = new Type(type);
            upper.dimension--;
            upper.isArray = (upper.dimension != 0);
            return new PtrType(convertType(upper));
        } else {
            if (type.isClass) {
                if (type.isString()) return new PtrType(new IntType(8));
                else return new PtrType(globalScope.getClassType(type.typeName));
            } else {
                if (type.isInt()) return new IntType(32);
                if (type.isBool()) return new IntType(1);
                if (type.isVoid()) return new VoidType();
                if (type.isNull()) return new PtrType(new IntType(32));
                throw new IRError(new Position(0, 0), "undefined type");
            }
        }
    }

    private final HashMap<String, Integer> varRecord = new HashMap<>();

    private String rename(String name) {
        var times = varRecord.get(name);
        String renew = name;
        if (times == null) {
            times = 0;
        } else {
            renew += "." + times;
        }
        varRecord.put(name, ++times);
        return renew;
    }

    private Entity getValue(ExprNode node) {
        if (node.val != null) return node.val;
        else return new LoadInst(rename(tempName()), node.ptr, curBlock);
    }

    private int tempNum = 0;

    private String tempName() {
        return "%_" + (tempNum++);
    }

    private Function getStrFunc(String op) {
        String name;
        switch (op) {
            case "+" -> name = "add";
            case ">" -> name = "sgt";
            case "<" -> name = "slt";
            case ">=" -> name = "sge";
            case "<=" -> name = "sle";
            case "!=" -> name = "ne";
            case "==" -> name = "eq";
            default -> throw new IRError(new Position(0, 0), "unknown str operator");
        }
        return globalScope.getFunction("_str_" + name);
    }

    public String toStr(String str) {
        return str.substring(1, str.length() - 1)
                .replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\\\", "\\");
    }
}



