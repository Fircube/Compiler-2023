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
            runFunc(func);
        }
    }

    public void runFunc(Function func){
        for (var block : func.blocks) {
            block.preds.clear();
            block.nexts.clear();
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
                if (block.nexts.size() == 1) {
                    Block next = block.nexts.get(0);
                    if (next.preds.size() == 1) {
                        block.insts.removeLast();
                        block.nexts.clear();
                        block.nexts.addAll(next.nexts);
                        for (var inst : next.insts) {
                            inst.belonging = block;
                            block.insts.add(inst);
                        }
                        for (var phiInst : next.phiInsts) {
                            phiInst.belonging = block;
                            block.phiInsts.add(phiInst);
                        }
                        for (var n : next.nexts) {
                            n.preds.remove(next);
                            n.preds.add(block);
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
            if (block.preds.isEmpty()) {
                iter.remove();
                for (var n : block.nexts) {
                    n.preds.remove(block);
                    if (n.preds.isEmpty()) iter = func.blocks.iterator();
                }
            }
        }
    }

    private void link(Block prev, Block next) {
        prev.nexts.add(next);
        next.preds.add(prev);
    }
}
