package src;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import src.ast.ASTBuilder;
import src.ast.rootNode.ProgramNode;
import src.frontend.SemanticChecker;
import src.frontend.SymbolCollector;
import src.parser.MxLexer;
import src.parser.MxParser;
import src.utils.error.MxError;
import src.utils.error.MxErrorListener;
import src.utils.scope.GlobalScope;

import java.io.FileInputStream;
import java.io.InputStream;

public class Compiler {
    public static void main(String[] args) throws Exception {
        InputStream input = System.in;
        boolean online = true;

        if (!online) { //local
            input = new FileInputStream("testcases/sema/symbol-package/symbol-2.mx");
        }

        try {
            compile(input);
        } catch (MxError err) {
            System.err.println(err.toString());
            throw new RuntimeException();
        }
    }

    public static void compile(InputStream input) throws Exception {
        GlobalScope globalScope = new GlobalScope();
        globalScope.initialize();

        MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
        lexer.removeErrorListeners();
        lexer.addErrorListener(new MxErrorListener());

        MxParser parser = new MxParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(new MxErrorListener());

        ParseTree parseTreeRoot = parser.program();

        ASTBuilder astBuilder = new ASTBuilder();
        ProgramNode ASTRoot = (ProgramNode) astBuilder.visit(parseTreeRoot);

        new SymbolCollector(globalScope).visit(ASTRoot);
        new SemanticChecker(globalScope).visit(ASTRoot);
    }
}