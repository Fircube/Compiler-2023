package src.asm.operand;

import java.util.HashMap;
import java.util.HashSet;

public class PhysReg extends Reg {
    public String name;
//    public int size = 4;
    public static HashSet<PhysReg> callerSaved = new HashSet<>();
    public static HashSet<PhysReg> calleeSaved = new HashSet<>();
    public static HashMap<String, PhysReg> regs = new HashMap<>() {
        {
            // caller
            var reg = new PhysReg("ra");
            put("ra", reg);
//            callerSaved.add(reg);
            for (int i = 0; i < 7; ++i) {
                reg = new PhysReg("t" + i);
                put("t" + i, reg);
                callerSaved.add(reg);
            }
            for (int i = 0; i < 8; ++i) {
                reg = new PhysReg("a" + i);
                put("a" + i, reg);
                callerSaved.add(reg);
            }

            put("zero", new PhysReg("zero"));
            reg = new PhysReg("sp");
            put("sp", reg);
            calleeSaved.add(reg);
            for (int i = 0; i < 12; ++i) {
                reg = new PhysReg("s" + i);
                put("s" + i, reg);
                calleeSaved.add(reg);
            }
        }
    };

    public PhysReg(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
