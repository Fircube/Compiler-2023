package src.ast.stmtNode;

import src.ast.ASTVisitor;
import src.ast.exprNode.ExprNode;
import src.utils.Position;

public class ReturnStmtNode extends StmtNode {
    public ExprNode ret;

    public ReturnStmtNode(ExprNode ret, Position pos) {
        super(pos);
        this.ret = ret;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
