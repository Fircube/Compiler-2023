package src.ast.rootNode;

import src.ast.ASTNode;
import src.ast.ASTVisitor;
import src.ast.stmtNode.VarDefNode;
import src.utils.Position;

import java.util.ArrayList;

public class ClassDefNode extends ASTNode {
    public final String className;
    public ArrayList<VarDefNode> members = new ArrayList<>();
    public ClassConNode con = null;
    public ArrayList<FuncDefNode> func = new ArrayList<>();

    public ClassDefNode(String className, Position pos) {
        super(pos);
        this.className = className;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
