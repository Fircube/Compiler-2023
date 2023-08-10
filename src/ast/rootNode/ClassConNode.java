package src.ast.rootNode;

import src.ast.ASTNode;
import src.ast.ASTVisitor;
import src.ast.stmtNode.BlockNode;
import src.utils.Position;

public class ClassConNode extends ASTNode {
    public final String className;
    public BlockNode suite;

    public ClassConNode(String className, BlockNode suite, Position pos) {
        super(pos);
        this.className = className;
        this.suite = suite;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
