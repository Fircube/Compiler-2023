package src.ast.stmtNode;

import src.ast.ASTVisitor;
import src.ast.exprNode.ExprNode;
import src.ast.rootNode.TypeNode;
import src.utils.Position;

public class UnitVarDefNode extends StmtNode {
    public TypeNode typeName;
    public String name;
    public ExprNode expr;

    public UnitVarDefNode(TypeNode typeName, String name, ExprNode expr, Position pos) {
        super(pos);
        this.typeName = typeName;
        this.name = name;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
