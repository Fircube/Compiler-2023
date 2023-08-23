package src.ir;

import src.ir.inst.*;

public interface IRVisitor {
    public void visit(Function func);

    public void visit(Block func);


    public void visit(AllocaInst inst);

    public void visit(BinaryInst inst);

    public void visit(BitCastInst inst);

    public void visit(BrInst inst);

    public void visit(CallInst inst);

    public void visit(GetElementPtrInst inst);

    public void visit(IcmpInst inst);

    public void visit(LoadInst inst);

    public void visit(PhiInst inst);

    public void visit(RetInst inst);

    public void visit(StoreInst inst);
}