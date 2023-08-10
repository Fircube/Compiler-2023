package src.ast;

import src.utils.Position;

abstract public class ASTNode {
    public Position pos;

    public ASTNode(Position pos) {
        this.pos = pos;
    }

    abstract public void accept(ASTVisitor visitor);
}
