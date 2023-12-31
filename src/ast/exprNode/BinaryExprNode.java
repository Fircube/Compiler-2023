package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.utils.Position;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs, rhs;
    public String op;

    public BinaryExprNode(ExprNode lhs, ExprNode rhs, String op, Position pos) {
        super(lhs.type, false, pos);
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
