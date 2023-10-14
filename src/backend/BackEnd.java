package src.backend;

import src.asm.ASMPrinter;
import src.middleend.*;
import src.utils.scope.GlobalScope;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class BackEnd {
    public GlobalScope globalScope;
    private final boolean debug;

    public BackEnd(MiddleEnd middleEnd, boolean debug) throws Exception {
        this.globalScope = middleEnd.globalScope;
        this.debug = debug;

        new InstSelection(globalScope);
        printFile("noAlloc.s");

        new RegAllocator(globalScope).run();
        printFile("regAlloc.s");

        new StackAllocator(globalScope).run();
        printFile("stackAlloc.s");

        var out = new PrintStream(System.out);
        var asmPrinter = new ASMPrinter(out, globalScope);
        asmPrinter.print();
    }

    private void printFile(String filename) throws Exception {
        if (!this.debug) return;
        var outFile = new FileOutputStream(filename);
        var asmOut = new PrintStream(outFile);
        var asmPrinter = new ASMPrinter(asmOut, globalScope);
        asmPrinter.print();
        outFile.close();
    }
}
