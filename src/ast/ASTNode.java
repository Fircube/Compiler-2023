package src.ast;

import src.utils.Position;

public abstract class ASTNode {
    public Position pos;

    public ASTNode(Position pos) {
        this.pos = pos;
    }

    public abstract void accept(ASTVisitor visitor);
}
