package src.ast.rootNode;

import src.ast.ASTNode;
import src.ast.ASTVisitor;
import src.ast.stmtNode.BlockNode;
import src.utils.Position;


public class FuncDefNode extends ASTNode {
    public TypeNode returnType;
    public final String funcName;
    public ParamNode parameters;
    public BlockNode suite;

    public FuncDefNode(TypeNode returnType, String funcName, ParamNode parameters, BlockNode suite, Position pos) {
        super(pos);
        this.returnType = returnType;
        this.funcName = funcName;
        this.parameters = parameters;
        this.suite = suite;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}