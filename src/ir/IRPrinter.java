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

public class IRPrinter implements ASTVisitor {
    public PrintStream os;
    public String fileName;

    public GlobalScope globalScope;

    public IRPrinter(PrintStream os, GlobalScope globalScope) {
        this.os = os;
//        this.fileName = fileName;
        this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode it) {
        for (String key : globalScope.classTypes.keySet()) {
            ClassType classType = globalScope.classTypes.get(key);
            String s = null;
            if (!classType.memberTypes.isEmpty()) {
                for (int i = 0; i < classType.memberTypes.size() - 1; ++i) {
                    s += classType.memberTypes.get(i) + ", ";
                }
                s += classType.memberTypes.get(classType.memberTypes.size() - 1);
            }
            os.printf("%s = type {%s}\n", classType, s);
        }

        for (String key : globalScope.globalVars.keySet()) {
            os.println(globalScope.globalVars.get(key));
        }

        for (String key : globalScope.entities.keySet()) {
            os.println(globalScope.entities.get(key));
        }

        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            FuncType funcType = (FuncType) func.type;
            String s = null;
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

    @Override
    public void visit(ClassConNode it) {
    }

    @Override
    public void visit(ClassDefNode it) {
    }

    @Override
    public void visit(FuncDefNode it) {
    }

    @Override
    public void visit(ParamNode it) {
    }

    @Override
    public void visit(TypeNode it) {
    }

    @Override
    public void visit(UnitParamNode it) {
    }

    @Override
    public void visit(BlockNode it) {
    }

    @Override
    public void visit(BreakStmtNode it) {
    }

    @Override
    public void visit(ContinueStmtNode it) {
    }

    @Override
    public void visit(ExprStmtNode it) {

    }

    @Override
    public void visit(ForStmtNode it) {
    }

    @Override
    public void visit(IfStmtNode it) {
    }

    @Override
    public void visit(ReturnStmtNode it) {
    }


    @Override
    public void visit(UnitVarDefNode it) {
    }

    @Override
    public void visit(VarDefNode it) {
    }

    @Override
    public void visit(WhileStmtNode it) {
    }

    @Override
    public void visit(ArrayExprNode it) {
    }

    @Override
    public void visit(AssignExprNode it) {
    }

    @Override
    public void visit(BinaryExprNode it) {
    }

    @Override
    public void visit(FuncCallExprNode it) {
    }

    @Override
    public void visit(IdentifierNode it) {
    }

    @Override
    public void visit(LiteralNode it) {
    }

    @Override
    public void visit(MemberExprNode it) {
    }


    @Override
    public void visit(NewExprNode it) {
    }

    @Override
    public void visit(ParenExprNode it) {
    }

    @Override
    public void visit(PostExprNode it) {
    }

    @Override
    public void visit(PreExprNode it) {
    }

    @Override
    public void visit(TernaryExprNode it) {
    }

    @Override
    public void visit(UnaryExprNode it) {
    }
}



