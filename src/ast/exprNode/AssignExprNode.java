package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.utils.Position;

public class AssignExprNode extends ExprNode {
    public ExprNode lhs, rhs;

    public AssignExprNode(ExprNode lhs, ExprNode rhs, Position pos) {
        super(lhs.type, false, pos);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
