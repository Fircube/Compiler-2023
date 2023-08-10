package src.ast.rootNode;

import src.ast.ASTNode;
import src.ast.ASTVisitor;
import src.utils.Position;

public class UnitParamNode extends ASTNode {
    public TypeNode type;
    public String paramName;

    public UnitParamNode(TypeNode type, String paramName, Position pos) {
        super(pos);
        this.type = type;
        this.paramName = paramName;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
