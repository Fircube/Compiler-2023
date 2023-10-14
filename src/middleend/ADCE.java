package src.middleend;

import src.ir.Block;
import src.ir.Function;
import src.ir.inst.*;
import src.utils.scope.GlobalScope;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class ADCE {
    public GlobalScope globalScope;

    public ADCE(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    public void run() {
        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            new CFG(this.globalScope,false).runOnFunc(func);
            new DomTree(true).run(func);
            markLive(func);

            for (var block : func.blocks) {
                var iter1 = block.phiInsts.iterator();
                while (iter1.hasNext()) {
                    var inst = iter1.next();
                    if (liveInst.contains(inst)) continue;
                    iter1.remove();
                }
                var iter2 = block.insts.listIterator();
                while (iter2.hasNext()) {
                    var inst = iter2.next();
                    if (liveInst.contains(inst)) continue;
                    iter2.remove();
                    if (inst.isTerminalInst()) {
                        var dest = getDom(block);
                        iter2.add(new BrInst(dest, null));
                    }
                }
            }
        }
    }

    Queue<Inst> workList = new LinkedList<>();
    HashSet<Inst> liveInst = new HashSet<>();
    HashSet<Block> liveBlock = new HashSet<>();

    void markLive(Function func) {
        for (var block : func.blocks) {
            for (var inst : block.insts) {
                if (inst instanceof RetInst) markInstLive(inst);
                else if (inst instanceof StoreInst) markInstLive(inst);
                else if (inst instanceof CallInst) markInstLive(inst);
            }
        }

        while (!workList.isEmpty()) {
            var inst = workList.poll();
            markBlockLive(inst.belonging);

            for (var operand : inst.operands) {
                if (operand instanceof Inst i) {
                    markInstLive(i);
                } else if (operand instanceof Block b) {
                    markInstLive(b.insts.getLast());
                }
            }
        }
    }

    void markInstLive(Inst inst) {
        if (liveInst.contains(inst)) return;
        liveInst.add(inst);
        workList.offer(inst);
    }

    void markBlockLive(Block block) {
        if (liveBlock.contains(block))
            return;
        liveBlock.add(block);
        for (var dependence : block.domTreeNode.DF) {
            markInstLive(dependence.origin.insts.getLast());
        }
    }

    Block getDom(Block block) {
        var dom = block.domTreeNode.iDom;
        while (!liveBlock.contains(dom.origin)) {
            dom = dom.iDom;
        }
        return dom.origin;
    }
}
