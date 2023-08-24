package src.ir.constant;

import src.ir.type.*;

public class StringConst extends Const {
    public String value;

    public StringConst(String name, String value) {
        super(new PtrType(new ArrayType(new IntType(8), value.length() + 1)), name);
        this.value = value + "\0";
    }

    @Override
    public String toString() {
        return this.name + " = private unnamed_addr constant " + ((PtrType) type).baseType + " c\"" + output() + "\"";
    }

    public String output() {
        return value.replace("\\", "\\5C").
                replace("\0", "\\00")
                .replace("\n", "\\0A")
                .replace("\t", "\\09")
                .replace("\"", "\\22");

    }
}
