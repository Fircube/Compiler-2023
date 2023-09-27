package src.ir;

import src.ir.type.*;
import src.utils.scope.*;

import java.io.PrintStream;

public class IRPrinter {
    public PrintStream os;

    public GlobalScope globalScope;

    public IRPrinter(PrintStream os, GlobalScope globalScope) {
        this.os = os;
        this.globalScope = globalScope;
    }

    public void print() {
        for (String key : globalScope.builtinFunc.keySet()) {
            Function func = globalScope.builtinFunc.get(key);
            FuncType funcType = (FuncType) func.type;
            StringBuilder s = new StringBuilder();
            if (!funcType.paramTypes.isEmpty()) {
                for (int i = 0; i < funcType.paramTypes.size() - 1; ++i) {
                    s.append(funcType.paramTypes.get(i)).append(", ");
                }
                s.append(funcType.paramTypes.get(funcType.paramTypes.size() - 1));
            }
            os.printf("declare %s %s(%s)\n", funcType.retType, func.name, s);
        }
        os.println();

        for (String key : globalScope.classTypes.keySet()) {
            ClassType classType = globalScope.classTypes.get(key);
            StringBuilder s = new StringBuilder();
            if (!classType.memberTypes.isEmpty()) {
                for (int i = 0; i < classType.memberTypes.size() - 1; ++i) {
                    s.append(classType.memberTypes.get(i)).append(", ");
                }
                s.append(classType.memberTypes.get(classType.memberTypes.size() - 1));
            }
            os.printf("%s = type {%s}\n", classType, s);
        }
        os.println();

        for (String key : globalScope.globalVars.keySet()) {
            os.println(globalScope.globalVars.get(key));
        }
        os.println();

        for (String key : globalScope.stringConst.keySet()) {
            os.println(globalScope.stringConst.get(key));
        }
        os.println();

        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            FuncType funcType = (FuncType) func.type;
            StringBuilder s = new StringBuilder();
            if (!func.operands.isEmpty()) {
                for (int i = 0; i < func.operands.size() - 1; ++i) {
                    s.append(func.operands.get(i).nameWithType()).append(", ");
                }
                s.append(func.operands.get(funcType.paramTypes.size() - 1).nameWithType());
            }
            os.printf("define %s %s(%s) {\n", funcType.retType, func.name, s);
            for (var i : func.blocks) {
                os.printf("%s:\n", i.name);
                for (var j : i.phiInsts) {
                    os.println("  " + j);
                }
                for (var j : i.insts) {
                    os.println("  " + j);
                }
                os.println();
            }
            os.println("}\n");
        }
    }
}



