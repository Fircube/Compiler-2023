package src;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import src.asm.ASMPrinter;
import src.backend.InstSelection;
import src.frontend.FrontEnd;
import src.middleend.MiddleEnd;
import src.parser.MxLexer;
import src.parser.MxParser;
import src.utils.error.MxError;
import src.utils.error.MxErrorListener;
import src.utils.scope.GlobalScope;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class Compiler {
    public static void main(String[] args) throws Exception {
        InputStream input = System.in;
        boolean online = true;

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

        var frontEnd = new FrontEnd(parser.program(), globalScope);

        boolean debug = System.getProperty("user.name").equals("ymy929");
        var middleEnd = new MiddleEnd(frontEnd, debug);

        new InstSelection(globalScope);

//        var regAlloca = new RegAlloca(globalScope);
//        regAlloca.allocate();
//        var asmOutFile = new FileOutputStream("asm.s");
//        var asmOut = new PrintStream(asmOutFile);

        var asmOut = new PrintStream(System.out);
        var asmPrinter = new ASMPrinter(asmOut, globalScope);
        asmPrinter.print();

//        asmOutFile.close();
    }
}