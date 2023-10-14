package src.asm;

import src.asm.operand.Operand;
import src.asm.operand.Reg;

import java.util.ArrayList;

public class ASMFunction extends Operand {
    public String name;
    public ArrayList<ASMBlock> blocks = new ArrayList<>();
    public ASMBlock entryBlock;
    public ASMBlock exitBlock;
    public ArrayList<Reg> params = new ArrayList<>();

    public int stackCnt = 1;
    public int allocaCnt = 0;
    public int paramCnt = 0, spilledCnt =0;

    public ASMFunction(String name) {
        this.name = name;
    }
}
