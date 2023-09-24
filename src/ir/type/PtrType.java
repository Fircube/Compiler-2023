package src.ir.type;

public class PtrType extends BaseType {
    public BaseType baseType;

    public PtrType(BaseType baseType) {
        super(4);
        this.baseType = baseType;
    }

    @Override
    public String toString() {
//        return "ptr";
        return baseType + "*";
    }
}
