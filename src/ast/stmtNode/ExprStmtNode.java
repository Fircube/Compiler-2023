package src.ast.stmtNode;

import src.ast.ASTVisitor;
import src.ast.exprNode.ExprNode;
import src.utils.Position;

public class ExprStmtNode extends StmtNode {
    public ExprNode expr;

    public ExprStmtNode(ExprNode expr, Position pos) {
        super(pos);
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
