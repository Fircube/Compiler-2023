package src.middleend;

import src.ir.Block;
import src.ir.Entity;
import src.ir.Function;
import src.ir.inst.Inst;
import src.ir.inst.MvInst;
import src.ir.inst.StoreInst;
import src.utils.scope.GlobalScope;

public class PhiElimination {
    public GlobalScope globalScope;

    public PhiElimination(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    public void run() {
        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            for (var block : func.blocks) {
                for (var phi : block.phiInsts) {
                    Entity tmp = new Entity(phi.operands.get(0).type, "%phi." + phi.idx);
                    for (int i = 0; i < phi.operands.size(); i += 2) {
                        var move = new MvInst(tmp, phi.operands.get(i), null);
                        Block pred = (Block) phi.operands.get(i + 1);
                        pred.insts.add(pred.insts.size() - 1, move);
                        move.belonging = pred;
                    }
                    Inst m = new MvInst(phi, tmp, null);
                    block.insts.addFirst(m);
                    m.belonging = block;
                }
                block.phiInsts.clear();
            }
        }
    }
}
