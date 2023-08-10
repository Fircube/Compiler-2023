package src.ast.stmtNode;

import src.ast.ASTVisitor;
import src.ast.exprNode.ExprNode;
import src.utils.Position;

public class IfStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode thenStmt, elseStmt;

    //public scope trueScope,falseScope

    public IfStmtNode(ExprNode con, StmtNode trueStmt, StmtNode falseStmt, Position pos) {
        super(pos);
        this.condition = con;
        this.thenStmt = trueStmt;
        this.elseStmt = falseStmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
