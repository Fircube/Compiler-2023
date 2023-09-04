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
            os.printf("\t.type %s,@object\n", gVar.name);
            os.println("\t.section .data");
            os.printf("\t.globl %s\n", gVar.name);
            os.printf("%s:\n", gVar.name);
            if (gVar.size == 1) os.printf("\t.byte %d\n", gVar.init);
            else os.printf("\t.word %d\n", gVar.init);
            os.printf("\t.size %s, %d\n", gVar.name, gVar.size);
            os.println();
        }

        for (GlobalStr str : globalScope.gStrs) {
            os.printf("\t.type %s,@object\n", str.name);
            os.println("\t.section .rodata");
            os.printf("\t.globl %s\n", str.name);
            os.printf("%s:\n", str.name);
            os.printf("\t.asciz \"%s\"\n", str.formatted());
            os.printf("\t.size %s, %d\n", str.name, str.val.length());
            os.println();
        }

        os.println("\t.section .text");
        for (ASMFunction func : globalScope.funcs) {
            os.printf("\t.globl\t%s\n", func.name);
            os.printf("\t.type\t%s,@function\n", func.name);
            os.printf("%s:\n", func.name);
            for (ASMBlock block : func.blocks) {
                os.print(block.name + ":\n");
                for (Inst inst : block.insts) {
                    os.println("\t" + inst);
                }
            }
            os.println();
        }
    }
}
