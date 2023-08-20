package src.ir.type;

import src.ir.Block;

import java.util.ArrayList;

public class FuncType extends BaseType{
    public BaseType retType;
    public ArrayList<BaseType> paramTypes = new ArrayList<>();

    public FuncType(BaseType retType){
        super(0);
        this.retType=retType;
    }

    @Override
    public String toString() {
        return null;
    }
}
