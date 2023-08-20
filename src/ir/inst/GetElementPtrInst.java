package src.ir.inst;

import src.ir.Block;
import src.ir.Entity;
import src.ir.IRVisitor;
import src.ir.type.BaseType;
import src.ir.type.PtrType;

import java.util.ArrayList;
import java.util.Arrays;

public class GetElementPtrInst extends Inst {
    public Entity ptr;
    public ArrayList<Entity> indexList = new ArrayList<>();

    public GetElementPtrInst(BaseType retType, String name, Block belonging, Entity ptr, Entity... idx) {
        super(retType, name,belonging);
        this.ptr = ptr;
        indexList.addAll(Arrays.asList(idx));
    }

    @Override
    public String toString() {
        StringBuilder s= new StringBuilder(new StringBuilder("%s = getelementptr inbounds %s, %s, ".formatted(name(), ((PtrType) type).baseType, ptr.nameWithType())));
        if(!indexList.isEmpty()){
            s.append(indexList.get(0).nameWithType());
            for(int i=1;i<indexList.size();++i){
                s.append(", ");
                s.append(indexList.get(i).nameWithType());
            }
        }
        return s.toString();
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
