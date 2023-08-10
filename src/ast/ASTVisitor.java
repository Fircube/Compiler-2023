package src.ast;

import src.ast.exprNode.*;
import src.ast.rootNode.*;
import src.ast.stmtNode.*;

public interface ASTVisitor {
    void visit(ProgramNode it);

    void visit(ClassConNode it);

    void visit(ClassDefNode it);

    void visit(FuncDefNode it);

    void visit(ParamNode it);

    void visit(TypeNode it);

    void visit(UnitParamNode it);


    void visit(BlockNode it);

    void visit(BreakStmtNode it);

    void visit(ContinueStmtNode it);

    void visit(ExprStmtNode it);

    void visit(ForStmtNode it);

    void visit(IfStmtNode it);

    void visit(ReturnStmtNode it);

    void visit(UnitVarDefNode it);

    void visit(VarDefNode it);

    void visit(WhileStmtNode it);


    void visit(ArrayExprNode it);

    void visit(AssignExprNode it);

    void visit(BinaryExprNode it);

    void visit(FuncCallExprNode it);

    void visit(IdentifierNode it);

    void visit(LiteralNode it);

    void visit(MemberExprNode it);

    void visit(NewExprNode it);

    void visit(ParenExprNode it);

    void visit(PostExprNode it);

    void visit(PreExprNode it);

    void visit(TernaryExprNode it);

    void visit(UnaryExprNode it);

}
