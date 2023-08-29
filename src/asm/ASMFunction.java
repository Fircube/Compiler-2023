package src.asm;


import src.asm.operand.Operand;
import src.asm.operand.Reg;
import src.asm.operand.VirtReg;

import java.util.ArrayList;

public class ASMFunction extends Operand{
    public String name;
    public ArrayList<ASMBlock> blocks = new ArrayList<>();
    public ArrayList<Reg> params = new ArrayList<>();
    public ASMBlock entryBlock;
    public int allocaSize = 0;
    VirtReg cacheRa = new VirtReg();
    ArrayList<Reg> cacheRegs = new ArrayList<>();

    public ASMFunction(String name) {
        this.name = name;
    }
}
