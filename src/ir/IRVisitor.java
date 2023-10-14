package src.ir;

import src.ir.inst.*;

public interface IRVisitor {
    void visit(Function func);

    void visit(Block func);


    void visit(AllocaInst inst);

    void visit(BinaryInst inst);

    void visit(BitCastInst inst);

    void visit(BrInst inst);

    void visit(CallInst inst);

    void visit(GetElementPtrInst inst);

    void visit(IcmpInst inst);

    void visit(LoadInst inst);

    void visit(MoveInst inst);

    void visit(PhiInst inst);

    void visit(RetInst inst);

    void visit(StoreInst inst);
}