package src.ast.rootNode;

import src.ast.ASTNode;
import src.ast.ASTVisitor;
import src.utils.Position;

import java.util.ArrayList;

public class ParamNode extends ASTNode {
    public ArrayList<UnitParamNode> lists = new ArrayList<>();

    public ParamNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
