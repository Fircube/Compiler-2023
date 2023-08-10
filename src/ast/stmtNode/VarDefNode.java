package src.ast.stmtNode;

import src.ast.ASTVisitor;
import src.ast.rootNode.TypeNode;
import src.utils.Position;

import java.util.ArrayList;

public class VarDefNode extends StmtNode {
    public TypeNode type;
    public ArrayList<UnitVarDefNode> defs = new ArrayList<>();

    public VarDefNode(TypeNode type, Position pos) {
        super(pos);
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
