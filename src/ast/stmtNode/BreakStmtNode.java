package src.ast.stmtNode;

import src.ast.ASTVisitor;
import src.utils.Position;

public class BreakStmtNode extends StmtNode {
    public BreakStmtNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}