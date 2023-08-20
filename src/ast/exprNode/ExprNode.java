package src.ast.exprNode;

import src.ast.ASTNode;
//import src.ast.rootNode.FuncDefNode;
import src.ast.rootNode.FuncDefNode;
import src.ir.Entity;
import src.utils.Position;
import src.utils.Type;

public abstract class ExprNode extends ASTNode {
    public Type type;
    public boolean isLValue;
    public boolean isFunc;
    public FuncDefNode funcDef;

    public Entity val, ptr;

    public ExprNode(Type type, boolean isLValue, Position pos) {
        super(pos);
        this.type = type;
        this.isLValue = isLValue;
        this.isFunc = false;
    }
}
