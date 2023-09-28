package src.middleend;

import src.ir.Block;
import src.ir.Function;
import src.ir.inst.BrInst;
import src.ir.inst.RetInst;

import java.util.ArrayList;
import java.util.HashSet;

// SLT algorithm and Semi-NCA algorithm are used to construct the dominant tree
public class DomTree {
    private int dfn;
    ArrayList<DomTreeNode> dfnOrder = new ArrayList<>();

    boolean reversed;

    DomTreeNode exit=new DomTreeNode(null);

    public DomTree() {
        this.reversed = false;
    }

    public DomTree(boolean reversed) {
        this.reversed = reversed;
    }

    ArrayList<Block> pre(DomTreeNode node) {
        return reversed ? node.origin.nexts : node.origin.preds;
    }

    ArrayList<Block> suc(DomTreeNode node) {
        return reversed ? node.origin.preds : node.origin.nexts;
    }


    public void run(Function func) {
        findIDom(func);
        collectChildren(func);
        if (reversed) {
//            for (var i = func.blocks.size() - 1; i >= 0; i--) {
//                var block = func.blocks.get(i);
//                if (block.insts.get(block.insts.size() - 1) instanceof RetInst) {
//                    findDF(block.domTreeNode);
//                }
//            }
            findDF(exit);
        } else {
            findDF(func.entryBlock.domTreeNode);
        }
    }

    void findIDom(Function func) {
        clear(func);
        // find sDom
        if (reversed) {
            for (var i = func.blocks.size() - 1; i >= 0; i--) {
                var block = func.blocks.get(i);
                if (block.insts.get(block.insts.size() - 1) instanceof RetInst) {
                    dfs(exit, block.domTreeNode);
                }
            }
        } else {
            dfs(null, func.entryBlock.domTreeNode);
        }
        for (int i = dfn - 1; i >= 1; --i) {
            var node = dfnOrder.get(i);
            var pa = node.parent;
            var sDom = pa; // 树边半支配路径
            for (var pred : pre(node)) {
                var pre = pred.domTreeNode;
                if (pre.dfn < 0) continue;
                if (pre.dfn <= node.dfn && pre.dfn < sDom.dfn) { // 树边半支配路径
                    sDom = pre;
                } else if (pre.dfn > node.dfn) { // 非树边半支配路径
                    DomTreeNode tmp = eval(pre).sDom;
                    if (tmp.dfn < sDom.dfn) {
                        sDom = tmp;
                    }
                }
            }
            node.sDom = sDom;

            // STL algorithm
            sDom.bucket.add(node);
            link(pa, node);
            for (var v : pa.bucket) {
                var tmp = eval(v);
                if (tmp.sDom == v.sDom) v.iDom = pa;
                else v.dom = tmp;
            }
            pa.bucket.clear();
        }
        for (int i = 1; i < dfn; ++i) {
            var node = dfnOrder.get(i);
            if (node.dom != null)
                node.iDom = node.dom.iDom;
        }
    }

    void collectChildren(Function func) {
        for (var block : func.blocks) {
            var node = block.domTreeNode;
            if (node.iDom != null) node.iDom.children.add(node);
        }
    }

    void findDF(DomTreeNode node) {
        var tmp = new HashSet<DomTreeNode>();
        if(node != exit){
            for (var next : suc(node)) {
                var nxt = next.domTreeNode;
                if (nxt.iDom != node) tmp.add(nxt);
            }
        }
        for (var c : node.children) {
            findDF(c);
            for (var w : c.DF) {
                if (node == w || !node.isDom(w)) tmp.add(w);
            }
        }
        node.DF.clear();
        node.DF.addAll(tmp);
    }

    private void clear(Function func) {
        dfn = 0;
        dfnOrder.clear();
        for (var b : func.blocks) {
            b.domTreeNode.clear();
        }
    }

    private void dfs(DomTreeNode pa, DomTreeNode node) {
        if (node.dfn > 0) return;
        node.dfn = dfn;
        dfnOrder.add(node);
        node.parent = pa;
        dfn++;
        for (var next : suc(node)) {
            dfs(node, next.domTreeNode);
        }
    }

    // get min semi
    DomTreeNode eval(DomTreeNode node) {
        var anc = node.ancestor;
        if (anc.ancestor != null) { // ancestor已访问则更新 curMinSemi
            var tmp = eval(anc);
            node.ancestor = anc.ancestor;
            if (tmp.sDom.dfn < node.curMinSemi.sDom.dfn) {
                node.curMinSemi = tmp;
            }
        }
        return node.curMinSemi;
    }

    void link(DomTreeNode pa, DomTreeNode node) {
        node.ancestor = pa; // 访问过的节点 ancestor非 null
        node.curMinSemi = node;
    }
}
