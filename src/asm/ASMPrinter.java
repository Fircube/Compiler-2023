package src.asm;

import src.asm.inst.Inst;
import src.asm.operand.GlobalStr;
import src.asm.operand.GlobalVar;
import src.utils.scope.GlobalScope;

import java.io.PrintStream;

public class ASMPrinter {
    public PrintStream os;

    public GlobalScope globalScope;

    public ASMPrinter(PrintStream os, GlobalScope globalScope) {
        this.os = os;
        this.globalScope = globalScope;
    }


    public void print() {
        for (GlobalVar gVar : globalScope.gVars) {
            os.println("\t.data");
            os.printf("\t.globl %s\n", gVar.name);
            os.printf("%s:\n", gVar.name);
            if (gVar.size == 1) os.printf("\t.byte %d\n", gVar.init);
            else os.printf("\t.word %d\n", gVar.init);
        }

        for (GlobalStr str : globalScope.gStrs) {
            os.println("\t.rodata");
            os.printf("\t.globl %s\n", str.name);
            os.printf("%s:\n", str.name);
            os.printf("\t.asciz \"%s\"\n", str.formatted());
        }

        os.println("\t.text");
        for (ASMFunction func : globalScope.funcs) {
            os.printf("\t.globl\t%s\n", func.name);
            os.printf("%s:\n", func.name);
            for (ASMBlock block : func.blocks) {
                os.print(block.name + ":\n");
                for (Inst inst : block.insts) {
                    os.println("\t" + inst);
                }
            }
        }
    }
}
