package src.middleend;

import src.ir.Block;
import src.ir.Function;
import src.ir.inst.BrInst;
import src.utils.scope.GlobalScope;

public class CFG {
    public GlobalScope globalScope;
    public boolean def;

    public CFG(GlobalScope globalScope,boolean def) {
        this.globalScope = globalScope;
        this.def = def;
    }

    public void run() {
        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            runOnFunc(func);
        }
    }

    public void runOnFunc(Function func){
        for (var block : func.blocks) {
            block.pred.clear();
            block.succ.clear();
        }

        for (var block : func.blocks) {
            var terminal = block.insts.get(block.insts.size() - 1);
            if (terminal instanceof BrInst b) {
                if (b.isJump) {
                    link(block, b.dest());
                } else {
                    link(block, b.trueDest());
                    link(block, b.falseDest());
                }
            }
        }

        if(def){
            var iter = func.blocks.iterator();
            while (iter.hasNext()) {
                var block = iter.next();
                if (block.succ.size() == 1) {
                    Block next = block.succ.get(0);
                    if (next.pred.size() == 1) {
                        block.insts.removeLast();
                        block.succ.clear();
                        block.succ.addAll(next.succ);
                        for (var inst : next.insts) {
                            inst.belonging = block;
                            block.insts.add(inst);
                        }
                        for (var phiInst : next.phiInsts) {
                            phiInst.belonging = block;
                            block.phiInsts.add(phiInst);
                        }
                        for (var n : next.succ) {
                            n.pred.remove(next);
                            n.pred.add(block);
                        }
                        func.blocks.remove(next);
                        iter = func.blocks.iterator();
                    }
                }
            }
        }


        var iter = func.blocks.iterator();
        while (iter.hasNext()) {
            var block = iter.next();
            if (block == func.entryBlock) continue;
            if (block == func.exitBlock) continue;
            if (block.pred.isEmpty()) {
                iter.remove();
                for (var n : block.succ) {
                    n.pred.remove(block);
                    if (n.pred.isEmpty()) iter = func.blocks.iterator();
                }
            }
        }
    }

    private void link(Block prev, Block next) {
        prev.succ.add(next);
        next.pred.add(prev);
    }
}
