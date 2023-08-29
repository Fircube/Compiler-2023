package src.ast.stmtNode;

import src.ast.ASTVisitor;
import src.ast.exprNode.ExprNode;
import src.utils.Position;

public class WhileStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode body;

    public WhileStmtNode(ExprNode condition, StmtNode body, Position pos) {
        super(pos);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
