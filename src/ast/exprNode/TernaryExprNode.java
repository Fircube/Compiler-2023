package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.utils.Position;

public class TernaryExprNode extends ExprNode {
    public ExprNode condition, trueExpr, falseExpr;

    public TernaryExprNode(ExprNode con, ExprNode trueExpr, ExprNode falseExpr, Position pos) {
        super(trueExpr.type, false, pos);
        this.condition = con;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
