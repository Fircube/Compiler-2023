package src.middleend;

import src.ir.Block;
import src.ir.Function;
import src.ir.inst.BrInst;
import src.utils.scope.GlobalScope;

public class CFG {
    public GlobalScope globalScope;

    public CFG(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    public void run() {
        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
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
    }

    private void link(Block prev, Block next) {
        prev.nexts.add(next);
        next.preds.add(prev);
    }
}
