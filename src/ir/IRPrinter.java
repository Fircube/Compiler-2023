package src.ir;

import src.ast.ASTVisitor;
import src.ast.exprNode.*;
import src.ast.rootNode.*;
import src.ast.stmtNode.*;
import src.ir.type.*;
import src.utils.scope.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class IRPrinter{
    public PrintStream os;

    public GlobalScope globalScope;

    public IRPrinter(PrintStream os, GlobalScope globalScope) {
        this.os = os;
        this.globalScope = globalScope;
    }

    public void print() {
        for (String key : globalScope.buildinFunc.keySet()) {
            Function func = globalScope.buildinFunc.get(key);
            FuncType funcType = (FuncType)func.type;
            String s = "";
            if (!funcType.paramTypes.isEmpty()) {
                for (int i = 0; i < funcType.paramTypes.size() - 1; ++i) {
                    s += funcType.paramTypes.get(i) + ", ";
                }
                s += funcType.paramTypes.get(funcType.paramTypes.size() - 1);
            }
            os.printf("declare %s %s(%s)\n", funcType.retType, func.name, s);
        }
        os.println();

        for (String key : globalScope.classTypes.keySet()) {
            ClassType classType = globalScope.classTypes.get(key);
            String s = "";
            if (!classType.memberTypes.isEmpty()) {
                for (int i = 0; i < classType.memberTypes.size() - 1; ++i) {
                    s += classType.memberTypes.get(i) + ", ";
                }
                s += classType.memberTypes.get(classType.memberTypes.size() - 1);
            }
            os.printf("%s = type {%s}\n", classType, s);
        }
        os.println();

        for (String key : globalScope.globalVars.keySet()) {
            os.println(globalScope.globalVars.get(key));
        }
        os.println();

        for (String key : globalScope.entities.keySet()) {
            os.println(globalScope.entities.get(key));
        }
        os.println();

        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            FuncType funcType = (FuncType) func.type;
            String s = "";
            if (!func.params.isEmpty()) {
                for (int i = 0; i < func.params.size() - 1; ++i) {
                    s += func.params.get(i).nameWithType() + ", ";
                }
                s += func.params.get(funcType.paramTypes.size() - 1).nameWithType();
            }
            os.printf("define %s %s(%s) {\n", funcType.retType, func.name, s);
            for (var i : func.blocks) {
                os.printf("%s:\n", i.name);
                for (var j : i.insts) {
                    os.println("  " + j);
                }
                os.println();
            }
            os.println("}\n");
        }
    }
}



