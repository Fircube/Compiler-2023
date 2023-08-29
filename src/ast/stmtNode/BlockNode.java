package src.ast.stmtNode;

import src.ast.ASTVisitor;
import src.utils.Position;

import java.util.ArrayList;

public class BlockNode extends StmtNode {
    public ArrayList<StmtNode> stmts = new ArrayList<>();

    public BlockNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
