package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.utils.Position;

import java.util.ArrayList;

public class FuncCallExprNode extends ExprNode {
    public ExprNode funcName;
    public ArrayList<ExprNode> realParams = new ArrayList<>();

    public FuncCallExprNode(ExprNode funcName, Position pos) {
        super(null, false, pos);
        this.funcName = funcName;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
