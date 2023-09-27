package src.middleend;

import src.ir.Function;

import java.util.ArrayList;
import java.util.HashSet;

// SLT algorithm and Semi-NCA algorithm are used to construct the dominant tree
public class DomTree {
    private int dfn;
    ArrayList<DomTreeNode> dfnOrder = new ArrayList<>();

    public DomTree() {
    }

    public void run(Function func) {
        findIDom(func);
        collectChildren(func);
        findDF(func.entryBlock.domTreeNode);
    }

    void findIDom(Function func) {
        clear(func);
        // find sDom
        dfs(null, func.entryBlock.domTreeNode);
        for (int i = dfn - 1; i >= 1; --i) {
            var node = dfnOrder.get(i);
            var pa = node.parent;
            var sDom = pa; // 树边半支配路径
            for (var pred : node.origin.preds) {
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
        for (var next : node.origin.nexts) {
            var nxt = next.domTreeNode;
            if (nxt.iDom != node) tmp.add(nxt);
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
        for (var next : node.origin.nexts) {
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
