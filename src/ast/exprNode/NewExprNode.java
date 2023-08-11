package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.utils.Position;
import src.utils.Type;

import java.util.ArrayList;

public class NewExprNode extends ExprNode {
    public String typeName;
    public int dimension;
    public ArrayList<ExprNode> sizeParams = new ArrayList<>();

    public NewExprNode(Type type, Position pos) {
        super(type, false, pos);
        this.typeName = type.typeName;
        this.dimension = type.dimension;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
