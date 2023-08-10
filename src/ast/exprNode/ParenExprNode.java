package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.utils.Position;

public class ParenExprNode extends ExprNode{
    public ExprNode expr;

    public ParenExprNode(ExprNode expr,Position pos) {
        super(expr.type,expr.isLValue,pos);
        this.isFunc=expr.isFunc;
        this.funcDef = expr.funcDef;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
