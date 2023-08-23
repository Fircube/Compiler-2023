package src.frontend;

import src.ast.ASTVisitor;
import src.ast.exprNode.*;
import src.ast.rootNode.*;
import src.ast.stmtNode.*;
import src.utils.Type;
import src.utils.error.SemanticError;
import src.utils.scope.*;

import java.util.ArrayList;

public class SemanticChecker implements ASTVisitor {
    private final GlobalScope globalScope;
    private Scope currentScope;

    public SemanticChecker(GlobalScope globalScope) {
        this.currentScope = this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode it) {
        FuncDefNode mainFunc = globalScope.getFuncDef("main");
        if (mainFunc == null) {
            throw new SemanticError(it.pos, "Missing 'main' function");
        }
        if (!mainFunc.returnType.type.isInt()) {
            throw new SemanticError(it.pos, "The return type of 'main()' can only be int");
        }
        if (!mainFunc.parameters.lists.isEmpty()) {
            throw new SemanticError(it.pos, "'main' can not have parameter");
        }
        for (var i : it.defs) {
            i.accept(this);
        }
    }

    @Override
    public void visit(ClassConNode it) {
        currentScope = new FuncScope(new Type("void"), currentScope);
        it.suite.accept(this);
        currentScope = currentScope.getParentScope();
    }

    @Override
    public void visit(ClassDefNode it) {
        currentScope = globalScope.getClassScope(it.className);
        if (it.con != null) it.con.accept(this);
        for (var i : it.func) {
            i.accept(this);
        }
        currentScope = currentScope.getParentScope();
    }

    @Override
    public void visit(FuncDefNode it) {
        it.returnType.accept(this);
        currentScope = new FuncScope(it.returnType.type, currentScope);
        if (it.parameters != null) {
            it.parameters.accept(this);
        }
        it.suite.accept(this);
        if (!it.funcName.equals("main") && !it.returnType.type.isVoid() && !((FuncScope) currentScope).hasReturnStmt) {
            throw new SemanticError(it.pos, "Missing return");
        }
        currentScope = currentScope.getParentScope();
    }

    @Override
    public void visit(ParamNode it) {
        for (var i : it.lists) {
            i.accept(this);
        }
    }

    @Override
    public void visit(TypeNode it) {
        switch (it.type.typeName) {
            case "int", "bool", "string", "void", "null", "this" -> {
            }
            default -> {
                if (!globalScope.hasClassScope(it.type.typeName))
                    throw new SemanticError(it.pos, "Undefined Type");
            }
        }
    }

    @Override
    public void visit(UnitParamNode it) {
        currentScope.addVarDef(new UnitVarDefNode(it.type, it.paramName, null, it.pos));
    }

    @Override
    public void visit(BlockNode it) {
        currentScope = new Scope(currentScope);
        for (var i : it.stmts) {
            i.accept(this);
        }
        currentScope = this.currentScope.getParentScope();
    }

    @Override
    public void visit(BreakStmtNode it) {
        if (!currentScope.isInLoop()) {
            throw new SemanticError(it.pos, "Break Statement cannot be out of loop");
        }
    }

    @Override
    public void visit(ContinueStmtNode it) {
        if (!currentScope.isInLoop()) {
            throw new SemanticError(it.pos, "Continue Statement cannot be out of loop");
        }
    }

    @Override
    public void visit(ExprStmtNode it) {
        if (it.expr != null) it.expr.accept(this);
    }

    @Override
    public void visit(ForStmtNode it) {
        if (it.varInit != null) it.varInit.accept(this);
        else if (it.forInit != null) it.forInit.accept(this);
        if (it.forCon != null) {
            it.forCon.accept(this);
            if (!it.forCon.type.isBool()) {
                throw new SemanticError(it.pos, "the type of the condition of for statement should be bool");
            }
        }
        if (it.forStep != null) it.forStep.accept(this);
        currentScope = new LoopScope(currentScope);
        if (it.body != null) {
            it.body.accept(this);
        }
        currentScope = currentScope.getParentScope();
    }

    @Override
    public void visit(IfStmtNode it) {
        if (it.condition == null) throw new SemanticError(it.pos, "Lack Condition");
        it.condition.accept(this);
        if (!it.condition.type.isBool()) {
            throw new SemanticError(it.pos, "the type of the condition of if statement should be bool");
        }
        currentScope = new Scope(currentScope);
        it.thenStmt.accept(this);
        currentScope = currentScope.getParentScope();
        if (it.elseStmt != null) {
            currentScope = new Scope(currentScope);
            it.elseStmt.accept(this);
            currentScope = currentScope.getParentScope();
        }
    }

    @Override
    public void visit(ReturnStmtNode it) {
        Type retType = currentScope.getRetType();
        Type type;
        if (it.ret != null) {
            it.ret.accept(this);
            type = it.ret.type;
        } else {
            type = new Type("void");
        }
        if (type.cannotAssignedTo(retType)) {
            throw new SemanticError(it.pos, "Return type not matched");
        }
        currentScope.inFunc().hasReturnStmt = true;
    }

    @Override
    public void visit(UnitVarDefNode it) {
        it.type.accept(this);
        if (it.expr != null) {
            it.expr.accept(this);
            if (it.expr.type.isMismatch(it.type.type) && !it.expr.type.isNull()) {
                throw new SemanticError(it.pos, "Unmatched Type");
            }
        }
        currentScope.addVarDef(it);
    }

    @Override
    public void visit(VarDefNode it) {
        for (var i : it.defs) {
            i.accept(this);
        }
    }

    @Override
    public void visit(WhileStmtNode it) {
        if (it.condition == null) throw new SemanticError(it.pos, "Lack Condition");
        it.condition.accept(this);
        if (!it.condition.type.isBool()) {
            throw new SemanticError(it.pos, "the type of the condition of if statement should be bool");
        }
        currentScope = new LoopScope(currentScope);
        it.body.accept(this);
        currentScope = currentScope.getParentScope();
    }

    @Override
    public void visit(ArrayExprNode it) {
        it.arrayName.accept(this);
        if (!it.arrayName.type.isArray) {
            throw new SemanticError(it.arrayName.pos, "Non-arrays do not have subscript operations");
        }
        if (it.arrayName instanceof NewExprNode) {
            throw new SemanticError(it.arrayName.pos, "Creating array space layer should from the outer layer to the inner layer");
        }
        it.index.accept(this);
        if (!it.index.type.isInt()) {
            throw new SemanticError(it.index.pos, "index should be 'int'");
        }
        it.type = new Type(it.arrayName.type);
        it.type.dimension--;
        if (it.type.dimension == 0) it.type.isArray = false;
    }

    @Override
    public void visit(AssignExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        if (!it.lhs.isLValue) {
            throw new SemanticError(it.pos, "The left expression is not assignable");
        }
        if (it.rhs.type.cannotAssignedTo(it.lhs.type)) {
            throw new SemanticError(it.pos, "cannot assign " + it.rhs.type.typeName + " to " + it.lhs.type.typeName + "' type");
        }
        it.type = new Type(it.lhs.type);
    }

    @Override
    public void visit(BinaryExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        if (it.op.equals("==") || it.op.equals("!=")) {
            if (!it.lhs.type.isNull() || !it.rhs.type.isNull()) {
                if (it.lhs.type.cannotAssignedTo(it.rhs.type) && it.rhs.type.cannotAssignedTo(it.lhs.type)) {
                    throw new SemanticError(it.pos, "type mismatch in binary operation");
                }
            }
            it.type = new Type("bool");
            return;
        }
        if (it.lhs.type.isMismatch(it.rhs.type)) {
            throw new SemanticError(it.pos, "type mismatch in binary operation");
        }
        switch (it.op) {
            case "*", "/", "%", "-", "<<", ">>", "&", "|", "^" -> {
                if (!it.lhs.type.isInt()) {
                    throw new SemanticError(it.pos, "the op" + it.op + "can only be applied in int");
                }
                it.type = new Type("int");
            }
            case "+" -> {
                if (it.lhs.type.isString()) {
                    it.type = new Type("string");
                    return;
                }
                if (it.lhs.type.isInt()) {
                    it.type = new Type("int");
                    return;
                }
                throw new SemanticError(it.pos, "The op + can only be applied in int and string");
            }
            case ">", "<", ">=", "<=" -> {
                if (!it.lhs.type.isString() && !it.lhs.type.isInt()) {
                    throw new SemanticError(it.pos, "the op" + it.op + "can only be applied in int and string");
                }
                it.type = new Type("bool");
            }
            case "&&", "||" -> {
                if (!it.lhs.type.isBool()) {
                    throw new SemanticError(it.pos, "the op" + it.op + "can only be applied in bool");
                }
                it.type = new Type("bool");
            }
        }
    }

    @Override
    public void visit(FuncCallExprNode it) {
        it.funcName.accept(this);
        boolean inClass = (currentScope.inClass() != null);
        ArrayList<UnitParamNode> oriParam = null;
        if (it.funcName.funcDef.parameters == null) {
            if (!it.realParams.isEmpty()) {
                oriParam = getUnitParamNodes(it, inClass);
            }
        } else {
            oriParam = it.funcName.funcDef.parameters.lists;
            if (oriParam.size() != it.realParams.size()) {
                oriParam = getUnitParamNodes(it, inClass);
            }
        }
        for (int i = 0; i < it.realParams.size(); ++i) {
            it.realParams.get(i).accept(this);
            if (it.realParams.get(i).type.cannotAssignedTo(oriParam.get(i).type.type)) {
                throw new SemanticError(it.pos, "parameter's type isn't the same as definition");
            }
        }
        it.type = new Type(it.funcName.funcDef.returnType.type);
    }

    private ArrayList<UnitParamNode> getUnitParamNodes(FuncCallExprNode it, boolean inClass) {
        ArrayList<UnitParamNode> oriParam = null;
        if (inClass) {
            it.funcName.funcDef = globalScope.getFuncDef(((IdentifierNode) it.funcName).name);
            if (it.funcName.funcDef.parameters == null) {
                if (!it.realParams.isEmpty()) {
                    throw new SemanticError(it.pos, "number of param not match");
                }
            } else {
                oriParam = it.funcName.funcDef.parameters.lists;
                if (oriParam.size() != it.realParams.size()) {
                    throw new SemanticError(it.pos, "number of param not match");
                }
            }
        } else throw new SemanticError(it.pos, "number of param not match");
        return oriParam;
    }

    @Override
    public void visit(IdentifierNode it) {
        if (it.isFunc) {
            ClassScope classScope = currentScope.inClass();
            FuncDefNode funcDef = null;
            if (classScope != null) funcDef = classScope.getFuncDef(it.name);
            if (funcDef == null) funcDef = globalScope.getFuncDef(it.name);
            if (funcDef == null) throw new SemanticError(it.pos, "the function hasn't declared");
            it.funcDef = funcDef;
        } else {
            var varDef = currentScope.getVarDef(it.name, false);
            if (varDef == null) throw new SemanticError(it.pos, "the variable hasn't declared");
            it.varDef = varDef;
            it.type = varDef.type.type;
        }
    }

    @Override
    public void visit(LiteralNode it) {
        ClassScope classScope = currentScope.inClass();
        if (it.type.typeName != null && it.type.isThis()) {
            if (classScope == null) {
                throw new SemanticError(it.pos, "'this' cannot be used out of a class");
            }
            it.type = new Type(classScope.className);
        }
    }

    @Override
    public void visit(MemberExprNode it) {
        it.className.accept(this);
        if (it.className.type.isArray) {
            if (!(it.member instanceof FuncCallExprNode) || !((IdentifierNode) ((FuncCallExprNode) it.member).funcName).name.equals("size")) {
                throw new SemanticError(it.pos, "Array type only has member size()");
            }
            it.type = new Type("int");
            it.funcDef = globalScope.getFuncDef("size");
            return;
        }
        if (!it.className.type.isClass) {
            throw new SemanticError(it.pos, "type " + it.className.type.typeName + " isn't a class type");
        }
        ClassScope classScope = globalScope.getClassScope(it.className.type.typeName);
        if (it.member.isFunc) {
            FuncCallExprNode funcCall = (FuncCallExprNode) it.member;
            FuncDefNode funcDef = funcCall.funcName.funcDef = classScope.getFuncDef(((IdentifierNode) funcCall.funcName).name);
            if (funcDef == null) {
                throw new SemanticError(it.pos, "no such member" + it.member.funcDef.funcName);
            }

            ArrayList<UnitParamNode> oriParam = null;
            if (funcDef.parameters == null) {
                if (!funcCall.realParams.isEmpty()) {
                    throw new SemanticError(it.pos, "number of param not match");
                }
            } else {
                oriParam = funcCall.funcName.funcDef.parameters.lists;
                if (oriParam.size() != funcCall.realParams.size()) {
                    throw new SemanticError(it.pos, "number of param not match");
                }
            }
            for (int i = 0; i < funcCall.realParams.size(); ++i) {
                funcCall.realParams.get(i).accept(this);
                if (funcCall.realParams.get(i).type.cannotAssignedTo(oriParam.get(i).type.type)) {
                    throw new SemanticError(it.pos, "parameter's type isn't the same as definition");
                }
            }
            it.member.type = new Type(funcCall.funcName.funcDef.returnType.type);

            it.type = funcDef.returnType.type;
            it.funcDef = funcDef;
        } else {
            UnitVarDefNode varDef = classScope.getVarDef(((IdentifierNode) it.member).name, true);
            if (varDef == null) {
                throw new SemanticError(it.pos, "no such member" + ((IdentifierNode) it.member).name);
            }
            it.type = varDef.type.type;
            it.varDef = varDef;
        }
    }


    @Override
    public void visit(NewExprNode it) {
        for (var i : it.sizeParams) {
            i.accept(this);
            if (!i.type.isInt()) {
                throw new SemanticError(i.pos, "array size expression must be int type");
            }
        }
    }

    @Override
    public void visit(ParenExprNode it) {
        it.expr.accept(this);
        it.type = new Type(it.expr.type);
        it.isLValue = it.expr.isLValue;
        it.isFunc = it.expr.isFunc;
        it.funcDef = it.expr.funcDef;
    }

    @Override
    public void visit(PostExprNode it) {
        it.expr.accept(this);
        if (!it.expr.isLValue) {
            throw new SemanticError(it.expr.pos, "Rvalues are not modifiable");
        }
        if (!it.expr.type.isInt()) {
            throw new SemanticError(it.expr.pos, "post increment and decrement can only be applied to int");
        }
        it.type = new Type(it.expr.type);
    }

    @Override
    public void visit(PreExprNode it) {
        it.expr.accept(this);
        if (!it.expr.isLValue) {
            throw new SemanticError(it.expr.pos, "Rvalues are not modifiable");
        }
        if (!it.expr.type.isInt()) {
            throw new SemanticError(it.expr.pos, "pre increment and decrement can only be applied to int");
        }
        it.type = new Type(it.expr.type);
    }

    @Override
    public void visit(TernaryExprNode it) {
        it.condition.accept(this);
        if (!it.condition.type.isBool()) {
            throw new SemanticError(it.pos, "the type of the condition of ternary operation should be bool");
        }
        it.trueExpr.accept(this);
        it.falseExpr.accept(this);
        if (it.trueExpr.type.isMismatch(it.falseExpr.type) && !it.trueExpr.type.isNull() && !it.falseExpr.type.isNull()) {
            throw new SemanticError(it.pos, "type mismatch in ternary operation");
        }
        it.type = new Type(it.trueExpr.type);
    }

    @Override
    public void visit(UnaryExprNode it) {
        it.expr.accept(this);
        if (it.op.equals("+") || it.op.equals("-") || it.op.equals("~")) {
            if (!it.expr.type.isInt()) {
                throw new SemanticError(it.pos, it.op + "can only be applied to int");
            }
        } else { //"!"
            if (!it.expr.type.isBool()) {
                throw new SemanticError(it.pos, "the op ! can only be applied to int");
            }
        }
        it.type = new Type(it.expr.type);
    }
}



