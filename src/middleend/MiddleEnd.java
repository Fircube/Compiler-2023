package src.middleend;

import src.frontend.FrontEnd;
import src.ir.IRBuilder;
import src.ir.IRPrinter;
import src.utils.scope.GlobalScope;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class MiddleEnd {
    public IRBuilder irBuilder;
    public GlobalScope globalScope;
    private final boolean debug;

    public MiddleEnd(FrontEnd frontEnd, boolean debug) throws Exception {
        this.globalScope = frontEnd.globalScope;
        this.debug = debug;
        globalScope.initIR();
        irBuilder = new IRBuilder(globalScope);
        irBuilder.visit(frontEnd.ASTRoot);
        printFile("ir.ll");

        new CFG(globalScope,true).run();
        printFile("cfg.ll");

        new Mem2Reg(globalScope,irBuilder).run();
        printFile("mem2reg.ll");

//        new ADCE(globalScope).run();
//        printFile("adce.ll");
//
//        new CFG(globalScope,true).run();
//        printFile("cfg2.ll");

        new PhiElimination(globalScope).run();
        printFile("phiEli.ll");

        new CFG(globalScope,true).run();
        printFile("final.ll");

//        var out = new PrintStream(System.out);
//        var irPrinter = new IRPrinter(out, globalScope);
//        irPrinter.print();
    }

    private void printFile(String filename) throws Exception {
        if (!this.debug) return;
        var outFile = new FileOutputStream(filename);
        var out = new PrintStream(outFile);
        var irPrinter = new IRPrinter(out, globalScope);
        irPrinter.print();
        outFile.close();
    }
}
