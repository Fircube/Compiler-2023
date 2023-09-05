package src.asm;

import src.asm.operand.Operand;

import java.util.ArrayList;

public class ASMFunction extends Operand {
    public String name;
    public ArrayList<ASMBlock> blocks = new ArrayList<>();
    public ASMBlock entryBlock;
    public int stackSpace = 0;
    public int paramSpace = 0, allocaSpace = 8;

    public ASMFunction(String name) {
        this.name = name;
    }
}
