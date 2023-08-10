package src.ast.stmtNode;

import src.ast.ASTNode;
import src.utils.Position;

public abstract class StmtNode extends ASTNode {
    public StmtNode(Position pos) {
        super(pos);
    }
}
