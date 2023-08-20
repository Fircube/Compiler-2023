package src.ast.rootNode;

import src.ast.ASTNode;
import src.ast.ASTVisitor;
import src.ir.type.BaseType;
import src.utils.Position;
import src.utils.Type;

public class TypeNode extends ASTNode {
    public Type type;

    public BaseType irType;

    public TypeNode(String name, Position pos) {
        super(pos);
        this.type = new Type(name);
    }

    public TypeNode(String name, boolean isClass, Position pos) {
        super(pos);
        this.type = new Type(name);
        this.type.isClass = isClass;
    }

    public TypeNode(String name, boolean isClass, int dimension, Position pos) {
        super(pos);
        this.type = new Type(name);
        this.type.isClass = isClass;
        this.type.isArray = true;
        this.type.dimension = dimension;
    }

    public boolean isBool() {
        return type.isBool();
    }

    public boolean isInt() {
        return type.isInt();
    }

    public boolean isString() {
        return type.isString();
    }

    public boolean isVoid() {
        return type.isVoid();
    }

    public boolean isNull() {
        return type.isVoid();
    }

    public boolean isThis() {
        return type.isThis();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
