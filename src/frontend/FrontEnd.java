package src.frontend;
import org.antlr.v4.runtime.tree.ParseTree;
import src.ast.ASTBuilder;
import src.ast.rootNode.ProgramNode;
import src.utils.scope.GlobalScope;

public class FrontEnd {
    public ProgramNode ASTRoot;
    public GlobalScope globalScope;

    public FrontEnd(ParseTree parseTreeRoot,GlobalScope gScope) {
        ASTRoot= (ProgramNode) new ASTBuilder().visit(parseTreeRoot);
        globalScope = gScope;
        new SymbolCollector(gScope).visit(ASTRoot);
        new SemanticChecker(gScope).visit(ASTRoot);
    }
}
