package src.ast.stmtNode;

import src.ast.ASTVisitor;
import src.ast.exprNode.ExprNode;
import src.utils.Position;

public class ForStmtNode extends StmtNode {
    public VarDefNode varInit;
    public ExprNode forInit, forCon, forStep;
    public StmtNode body;

    //public loopScope scope

    public ForStmtNode(VarDefNode varInit, ExprNode forInit, ExprNode forCon, ExprNode forStep, StmtNode body, Position pos) {
        super(pos);
        this.varInit = varInit;
        this.forInit = forInit;
        this.forCon = forCon;
        this.forStep = forStep;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
