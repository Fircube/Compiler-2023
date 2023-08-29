package src;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import src.asm.ASMPrinter;
import src.asm.InstSelection;
import src.ast.ASTBuilder;
import src.ast.rootNode.ProgramNode;
import src.frontend.SemanticChecker;
import src.frontend.SymbolCollector;
import src.ir.IRBuilder;
import src.ir.IRPrinter;
import src.parser.MxLexer;
import src.parser.MxParser;
import src.utils.error.MxError;
import src.utils.error.MxErrorListener;
import src.utils.scope.GlobalScope;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class Compiler {
    public static void main(String[] args) throws Exception {
        InputStream input = System.in;
        boolean online = false;

        if (!online) { //local
            input = new FileInputStream("input.mx");
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
        globalScope.initAST();

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

        IRBuilder irBuilder = new IRBuilder(globalScope);
        irBuilder.visit(ASTRoot);
//        var outFile = new FileOutputStream("ir.ll");
//        var out = new PrintStream(outFile);
//        var out = new PrintStream(System.out);
//        var irPrinter = new IRPrinter(out, globalScope);
//        irPrinter.print();
//        outFile.close();

        new InstSelection(globalScope);
        var asmOutFile = new FileOutputStream("ir.ll");
        var asmOut = new PrintStream(asmOutFile);
        var asmPrinter = new ASMPrinter(asmOut, globalScope);
        asmPrinter.print();
        asmOutFile.close();
    }
}