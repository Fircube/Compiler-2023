package src.frontend;

import src.ast.ASTVisitor;
import src.ast.exprNode.*;
import src.ast.rootNode.*;
import src.ast.stmtNode.*;
import src.utils.scope.ClassScope;
import src.utils.scope.GlobalScope;

public class SymbolCollector implements ASTVisitor {
    public GlobalScope globalScope;
    public ClassScope classScope = null;

    public SymbolCollector(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode it) {
        for (var i : it.defs) {
            if (i instanceof VarDefNode) {
                continue;
            }
            i.accept(this);
        }
    }

    @Override
    public void visit(ClassConNode it) {
        classScope.con = it;
    }

    @Override
    public void visit(ClassDefNode it) {
        classScope = new ClassScope(it.className, globalScope);
        for (var i : it.members) {
            i.accept(this);
        }
        for (var i : it.con) {
            i.accept(this);
        }
        for (var i : it.func) {
            i.accept(this);
        }
        globalScope.addClassDef(classScope, it.pos);
        this.classScope = null;
    }

    @Override
    public void visit(FuncDefNode it) {
        if (classScope != null) {
            classScope.addFuncDef(it);
        } else {
            globalScope.addFuncDef(it);
        }
    }

    @Override
    public void visit(ParamNode it) {
    }

    @Override
    public void visit(TypeNode it) {
    }

    @Override
    public void visit(UnitParamNode it) {
    }

    @Override
    public void visit(BlockNode it) {
    }

    @Override
    public void visit(BreakStmtNode it) {
    }

    @Override
    public void visit(ContinueStmtNode it) {
    }

    @Override
    public void visit(ExprStmtNode it) {
    }

    @Override
    public void visit(ForStmtNode it) {
    }

    @Override
    public void visit(IfStmtNode it) {
    }

    @Override
    public void visit(ReturnStmtNode it) {
    }

    @Override
    public void visit(UnitVarDefNode it) {
        classScope.addVarDef(it);
    }

    @Override
    public void visit(VarDefNode it) {
        for (var i : it.defs) {
            i.accept(this);
        }
    }

    @Override
    public void visit(WhileStmtNode it) {
    }


    @Override
    public void visit(ArrayExprNode it) {
    }

    @Override
    public void visit(AssignExprNode it) {
    }

    @Override
    public void visit(BinaryExprNode it) {
    }

    @Override
    public void visit(FuncCallExprNode it) {
    }

    @Override
    public void visit(IdentifierNode it) {
    }

    @Override
    public void visit(LiteralNode it) {
    }

    @Override
    public void visit(MemberExprNode it) {
    }


    @Override
    public void visit(NewExprNode it) {
    }

    @Override
    public void visit(ParenExprNode it) {
    }

    @Override
    public void visit(PostExprNode it) {
    }

    @Override
    public void visit(PreExprNode it) {
    }

    @Override
    public void visit(TernaryExprNode it) {
    }

    @Override
    public void visit(UnaryExprNode it) {
    }
}
