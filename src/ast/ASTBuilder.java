package src.ast;

import src.ast.exprNode.*;
import src.ast.rootNode.*;
import src.ast.stmtNode.*;
import src.utils.Position;
import src.parser.*;
import src.parser.MxParser.*;
import src.utils.Type;
import src.utils.error.SemanticError;

public class ASTBuilder extends MxParserBaseVisitor<ASTNode> {
    @Override
    public ASTNode visitProgram(ProgramContext ctx) {
        ProgramNode root = new ProgramNode(new Position(ctx));
        for (var i : ctx.children) {
            if (i instanceof FuncDefContext || i instanceof ClassDefContext || i instanceof VarDefContext) {
                root.defs.add(visit(i));
            }
        }
        return root;
    }

    @Override
    public ASTNode visitFuncDef(FuncDefContext ctx) {
        TypeNode returnType = (TypeNode) visit(ctx.returnType());
        ParamNode params = new ParamNode(new Position(ctx));
        BlockNode suite = (BlockNode) visit(ctx.suite());
        if (ctx.parameter() != null) params = (ParamNode) visit(ctx.parameter());
        return new FuncDefNode(returnType, ctx.Identifier().getText(), params, suite, new Position(ctx));
    }

    @Override
    public ASTNode visitParameter(ParameterContext ctx) {
        ParamNode params = new ParamNode(new Position(ctx));
        for (var i : ctx.unitParameter()) {
            params.lists.add((UnitParamNode) visit(i));
        }
        return params;
    }

    @Override
    public ASTNode visitUnitParameter(UnitParameterContext ctx) {
        TypeNode type = (TypeNode) visit(ctx.defType());
        return new UnitParamNode(type, ctx.Identifier().getText(), new Position(ctx));
    }

    @Override
    public ASTNode visitClassDef(ClassDefContext ctx) {
        ClassDefNode newClass = new ClassDefNode(ctx.Identifier().getText(), new Position(ctx));
        for (var i : ctx.varDef()) {
            newClass.members.add((VarDefNode) visit(i));
        }
        if (ctx.classConstructor() != null) {
            if (ctx.classConstructor().size() > 1)
                throw new SemanticError(new Position(ctx), "Multiple definition of Constructor");
            for (var i : ctx.classConstructor()) {
                ClassConNode con = (ClassConNode) visit(i);
                if (!con.className.equals(newClass.className)) {
                    throw new SemanticError(new Position(i), "The constructor name does not match the class name.");
                }
                newClass.con=con;
            }
        }
        for (var i : ctx.funcDef()) {
            newClass.func.add((FuncDefNode) visit(i));
        }
        return newClass;
    }

    @Override
    public ASTNode visitClassConstructor(ClassConstructorContext ctx) {
        BlockNode suite = (BlockNode) visit(ctx.suite());
        return new ClassConNode(ctx.Identifier().getText(), suite, new Position(ctx));
    }

    @Override
    public ASTNode visitVarDef(VarDefContext ctx) {
        TypeNode type = (TypeNode) visit(ctx.defType());
        VarDefNode varDef = new VarDefNode(type, new Position(ctx));
        for (var i : ctx.unitVarDef()) {
            UnitVarDefNode unit = (UnitVarDefNode) visit(i);
            unit.type = type;
            varDef.defs.add(unit);
        }
        return varDef;
    }

    @Override
    public ASTNode visitUnitVarDef(UnitVarDefContext ctx) {
        ExprNode expr = null;
        if (ctx.expression() != null) expr = (ExprNode) visit(ctx.expression());
        return new UnitVarDefNode(null, ctx.Identifier().getText(), expr, new Position(ctx));
    }

    @Override
    public ASTNode visitReturnType(ReturnTypeContext ctx) {
        if (ctx.Void() != null) {
            return new TypeNode("void", false, new Position(ctx));
        }
        return visit(ctx.defType());
    }

    @Override
    public ASTNode visitDefType(DefTypeContext ctx) {
        String name;
        boolean isClass;
        if (ctx.Identifier() != null) {
            name = ctx.Identifier().getText();
            isClass = true;
        } else {
            TypeNode tmp = (TypeNode) visit(ctx.baseType());
            name = tmp.type.typeName;
            isClass = tmp.type.isClass;
        }
        int dimension = ctx.LeftBracket().size();
        if (dimension == 0) {
            return new TypeNode(name, isClass, new Position(ctx));
        } else {
            return new TypeNode(name, isClass, dimension, new Position(ctx));
        }
    }

    @Override
    public ASTNode visitBaseType(BaseTypeContext ctx) {
        boolean isClass = ctx.getText().equals("string");
        return new TypeNode(ctx.getText(), isClass, new Position(ctx));
    }

    @Override
    public ASTNode visitSuite(SuiteContext ctx) {
        BlockNode suite = new BlockNode(new Position(ctx));
        for (var i : ctx.statement()) {
            suite.stmts.add((StmtNode) visit(i));
        }
        return suite;
    }

    @Override
    public ASTNode visitRealParameter(RealParameterContext ctx) {
        return null;
    }

    @Override
    public ASTNode visitBlock(BlockContext ctx) {
        return visit(ctx.suite());
    }

    @Override
    public ASTNode visitVarDefStmt(VarDefStmtContext ctx) {
        return visit(ctx.varDef());
    }

    @Override
    public ASTNode visitIfStmt(IfStmtContext ctx) {
        ExprNode condition = (ExprNode) visit(ctx.expression());
        StmtNode thenStmt = (StmtNode) visit(ctx.trueStmt);
        StmtNode elseStmt = null;
        if (ctx.statement().size() > 1) {
            elseStmt = (StmtNode) visit(ctx.falseStmt);
        }
        return new IfStmtNode(condition, thenStmt, elseStmt, new Position(ctx));
    }

    @Override
    public ASTNode visitWhileStmt(WhileStmtContext ctx) {
        ExprNode condition = (ExprNode) visit(ctx.expression());
        StmtNode stmt = (StmtNode) visit(ctx.statement());
        return new WhileStmtNode(condition, stmt, new Position(ctx));
    }

    @Override
    public ASTNode visitForStmt(ForStmtContext ctx) {
        VarDefNode varInit = null;
        ExprNode forInit = null, forCon = null, forStep = null;
        if (ctx.varInit != null) {
            varInit = (VarDefNode) visit(ctx.varInit);
        } else if (ctx.forInit != null) {
            forInit = (ExprNode) visit(ctx.forInit);
        }
        if (ctx.forCon != null) {
            forCon = (ExprNode) visit(ctx.forCon);
        }
        if (ctx.forStep != null) {
            forStep = (ExprNode) visit(ctx.forStep);
        }
        StmtNode body = (StmtNode) visit(ctx.statement());
        return new ForStmtNode(varInit, forInit, forCon, forStep, body, new Position(ctx));
    }

    @Override
    public ASTNode visitReturnStmt(ReturnStmtContext ctx) {
        ExprNode expr = null;
        if (ctx.expression() != null) {
            expr = (ExprNode) visit(ctx.expression());
        }
        return new ReturnStmtNode(expr, new Position(ctx));
    }

    @Override
    public ASTNode visitBreakStmt(BreakStmtContext ctx) {
        return new BreakStmtNode(new Position(ctx));
    }

    @Override
    public ASTNode visitContinueStmt(ContinueStmtContext ctx) {
        return new ContinueStmtNode(new Position(ctx));
    }

    @Override
    public ASTNode visitExprStmt(ExprStmtContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new ExprStmtNode(expr, new Position(ctx));
    }

    @Override
    public ASTNode visitEmptyStmt(EmptyStmtContext ctx) {
        return new ExprStmtNode(null, new Position(ctx));
    }

    @Override
    public ASTNode visitNewExpr(NewExprContext ctx) {
        TypeNode tmp, type;
        if (ctx.baseType() == null) {
            tmp = new TypeNode(ctx.Identifier().getText(), true, new Position(ctx));
        } else {
            tmp = (TypeNode) visit(ctx.baseType());
        }
        int dimension = ctx.LeftBracket().size();
        if (dimension == 0) {
            type = new TypeNode(tmp.type.typeName, tmp.type.isClass, new Position(ctx));
        } else {
            type = new TypeNode(tmp.type.typeName, tmp.type.isClass, dimension, new Position(ctx));
        }

        NewExprNode newExpr = new NewExprNode(type.type, new Position(ctx));
        for (var i : ctx.expression()) {
            newExpr.sizeParams.add((ExprNode) visit(i));
        }
        return newExpr;
    }

    @Override
    public ASTNode visitUnaryExpr(UnaryExprContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new UnaryExprNode(expr, ctx.prefix.getText(), new Position(ctx));
    }

    @Override
    public ASTNode visitTernaryExpr(TernaryExprContext ctx) {
        ExprNode con = (ExprNode) visit(ctx.expression(0));
        ExprNode trueExpr = (ExprNode) visit(ctx.expression(1));
        ExprNode falseExpr = (ExprNode) visit(ctx.expression(2));
        return new TernaryExprNode(con, trueExpr, falseExpr, new Position(ctx));
    }

    @Override
    public ASTNode visitArrayExpr(ArrayExprContext ctx) {
        ExprNode arrayName = (ExprNode) visit(ctx.expression(0));
        ExprNode index = (ExprNode) visit(ctx.expression(1));
        return new ArrayExprNode(arrayName, index, new Position(ctx));
    }

    @Override
    public ASTNode visitMemberExpr(MemberExprContext ctx) {
        ExprNode className = (ExprNode) visit(ctx.expression());
        ExprNode mem;
        Type type = new Type((String) null);
        if (ctx.LeftParen() == null) {
            mem = new IdentifierNode(ctx.Identifier().getText(), type, new Position(ctx));
        } else {
            ExprNode funcName = new IdentifierNode(ctx.Identifier().getText(), type, new Position(ctx));
            funcName.isLValue = false;
            funcName.isFunc = true;
            mem = new FuncCallExprNode(funcName, new Position(ctx));
            mem.isFunc = true;
            if (ctx.realParameter() != null) {
                for (var i : ctx.realParameter().expression()) {
                    ((FuncCallExprNode) mem).realParams.add((ExprNode) visit(i));
                }
            }
        }
        return new MemberExprNode(className, mem, new Position(ctx));
    }

    @Override
    public ASTNode visitAtomExpr(AtomExprContext ctx) {
        return visit(ctx.primary());
    }

    @Override
    public ASTNode visitBinaryExpr(BinaryExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.expression(0));
        ExprNode rhs = (ExprNode) visit(ctx.expression(1));
        return new BinaryExprNode(lhs, rhs, ctx.op.getText(), new Position(ctx));
    }

    @Override
    public ASTNode visitPreExpr(PreExprContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new PreExprNode(expr, ctx.prefix.getText(), new Position(ctx));
    }

    @Override
    public ASTNode visitFuncCallExpr(FuncCallExprContext ctx) {
        ExprNode funcName = (ExprNode) visit(ctx.expression());
        funcName.isLValue = false;
        funcName.isFunc = true;
        FuncCallExprNode func = new FuncCallExprNode(funcName, new Position(ctx));
        if (ctx.realParameter() != null) {
            for (var i : ctx.realParameter().expression()) {
                func.realParams.add((ExprNode) visit(i));
            }
        }
        return func;
    }

    @Override
    public ASTNode visitAssignExpr(AssignExprContext ctx) {
        ExprNode expr0 = (ExprNode) visit(ctx.expression(0));
        ExprNode expr1 = (ExprNode) visit(ctx.expression(1));
        return new AssignExprNode(expr0, expr1, new Position(ctx));
    }

    @Override
    public ASTNode visitPostExpr(PostExprContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new PostExprNode(expr, ctx.postfix.getText(), new Position(ctx));
    }

    @Override
    public ASTNode visitParen(ParenContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new ParenExprNode(expr, new Position(ctx));
    }

    @Override
    public ASTNode visitIdentify(IdentifyContext ctx) {
        Type type = new Type((String) null);
        return new IdentifierNode(ctx.getText(), type, new Position(ctx));
    }

    @Override
    public ASTNode visitLiteral(LiteralContext ctx) {
        String typeName = null;
        boolean isClass = false;
        if (ctx.ConstantInt() != null) {
            typeName = "int";
        } else if (ctx.ConstantStr() != null) {
            typeName = "string";
            isClass = true;
        } else if (ctx.True() != null || ctx.False() != null) {
            typeName = "bool";
        } else if (ctx.Null() != null) {
            typeName = "null";
        } else if (ctx.This() != null) {
            typeName = "this";
            isClass = true;
        }
        TypeNode type = new TypeNode(typeName, isClass, new Position(ctx));
        return new LiteralNode(ctx.getText(), type.type, new Position(ctx));
    }
}
