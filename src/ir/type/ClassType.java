package src.ir.type;

import java.util.ArrayList;

public class ClassType extends BaseType {
    public String className;
    public ArrayList<BaseType> memberTypes = new ArrayList<>();

    public ClassType(String className, int size) {
        super(size << 2);
        this.className = className;
    }

    @Override
    public String toString() {
        return this.className;
    }
}
