package src.backend;

import src.asm.ASMBlock;
import src.asm.ASMFunction;
import src.asm.operand.Reg;
import src.backend.utils.LANode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class LivenessAnalysis {
    private int dfn;

    HashMap<ASMBlock, HashSet<Reg>> defsMap = new HashMap<>();
    HashMap<ASMBlock, HashSet<Reg>> usesMap = new HashMap<>();
    PriorityQueue<LANode> queue = new PriorityQueue<>();

    public void runOnFunc(ASMFunction func) {
        for (var block : func.blocks) {
            block.liveIn.clear();
            block.liveOut.clear();
            integrate(block);
        }

        dfn = 1;
        dfs(func.exitBlock);

        queue.offer(func.exitBlock.laNode);
        while (!queue.isEmpty()) {
            var node = queue.poll();
            while (!queue.isEmpty() && queue.peek().equals(node)) {
                queue.poll();
            }
            var block = node.origin;

            // out[n] = U_{s in succ[n]} in[s]
            var liveOut_ = new HashSet<Reg>();
            for (var s : block.succ) {
                liveOut_.addAll(s.liveIn);
            }

            // in[n] = use[n] U (out[n] - def[n])
            var liveIn_ = new HashSet<>(liveOut_);
            liveIn_.removeAll(defsMap.get(block));
            liveIn_.addAll(usesMap.get(block));

            if (!liveIn_.equals(block.liveIn) || !liveOut_.equals(block.liveOut)) {
                block.liveIn.addAll(liveIn_);
                block.liveOut.addAll(liveOut_);
                for (var p : block.pred) {
                    if (p.laNode != null) queue.offer(p.laNode);
                }
            }
        }
    }

    //** The equivalent use/def of the BB */
    private void integrate(ASMBlock block) {
        var uses = new HashSet<Reg>();
        var defs = new HashSet<Reg>();

        for (var i : block.insts) {
            // use[pn] = use[p] U (use[n] − def[p])
            for (var u : i.uses()) {
                if (!defs.contains(u)) {
                    uses.add(u);
                }
            }
            // def[pn] = def[p] U def[n]
            defs.addAll(i.defs());
        }
        usesMap.put(block, uses);
        defsMap.put(block, defs);
    }

    private void dfs(ASMBlock block) {
        block.laNode = new LANode(dfn, block);
        dfn++;
        for (var b : block.pred) {
            if (b.laNode != null) continue;
            dfs(b);
        }
    }
}
