package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.ast.stmtNode.UnitVarDefNode;
import src.utils.Position;
import src.utils.Type;

public class IdentifierNode extends ExprNode {
    public String name;
    public UnitVarDefNode varDef;

    public IdentifierNode(String name, Type type, Position pos) {
        super(type, true, pos);
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
