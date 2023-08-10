package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.utils.Position;
import src.utils.Type;

public class LiteralNode extends ExprNode {
    public String content;

    public LiteralNode(String content, Type type, Position pos){
        super(type,false,pos);
        this.content=content;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
