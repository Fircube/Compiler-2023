package src.utils;


public class Type {
    public String typeName;
    public boolean isArray;
    public boolean isClass;
    public int dimension;

//    /public Type() {
//        this.isArray = false;
//        this.isClass = false;
//        this.dimension = 0;
//    }

    public Type(String name) {
        this.typeName = name;
        this.isArray = false;
        if (name == null) this.isClass = false;
        else this.isClass = !name.equals("int") && !name.equals("bool") && !name.equals("null");
        this.dimension = 0;
    }

    public Type(Type other) {
        this.typeName = other.typeName;
        this.isArray = other.isArray;
        this.isClass = other.isClass;
        this.dimension = other.dimension;
    }

    public boolean isBool() {
        return typeName.equals("bool") && !isArray;
    }

    public boolean isInt() {
        return typeName.equals("int") && !isArray;
    }

    public boolean isString() {
        return typeName.equals("string") && !isArray;
    }

    public boolean isVoid() {
        return typeName.equals("void");
    }

    public boolean isNull() {
        return typeName.equals("null");
    }

    public boolean isThis() {
        return typeName.equals("this");
    }


    // check if same name
    // Regardless of class and variable differences
    public boolean isMismatch(Type other) {
        return !this.typeName.equals(other.typeName) || this.isArray != other.isArray || this.dimension != other.dimension;
    }

    public boolean cannotAssignedTo(Type other) {
        if (typeName.equals("null")) return !other.isClass && !other.isArray;
        return this.isMismatch(other);
    }
}
