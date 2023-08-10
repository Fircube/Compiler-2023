package src.ast.exprNode;

import src.ast.ASTVisitor;
import src.ast.stmtNode.UnitVarDefNode;
import src.utils.Position;

public class MemberExprNode extends ExprNode {
    public ExprNode className, member;
    public UnitVarDefNode varDef;

    public MemberExprNode(ExprNode className, ExprNode member, Position pos) {
        super(member.type, true, pos);
        this.className = className;
        this.member = member;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
