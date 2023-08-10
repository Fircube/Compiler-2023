package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.utils.Position;

public class ArrayExprNode extends ExprNode {
    public ExprNode arrayName, index;
    public int dimension;

    public ArrayExprNode(ExprNode arrayName, ExprNode index, Position pos) {
        super(null, true, pos);
        this.arrayName = arrayName;
        this.index = index;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
