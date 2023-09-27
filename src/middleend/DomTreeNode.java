package src.middleend;

import src.ir.Block;

import java.util.ArrayList;

public class DomTreeNode {
    public Block origin;
    public int dfn;
    public DomTreeNode parent, sDom, dom, iDom;
    public ArrayList<DomTreeNode> bucket = new ArrayList<>();
    public DomTreeNode ancestor; // 记录当前未访问的最小祖先
    public DomTreeNode curMinSemi;

    public ArrayList<DomTreeNode> children = new ArrayList<>();
    public ArrayList<DomTreeNode> DF = new ArrayList<>();

    public DomTreeNode(Block origin) {
        this.origin = origin;
    }

    public void clear() {
        dfn = -1;
        parent = sDom = dom = iDom = ancestor = curMinSemi = null;
        bucket.clear();
        children.clear();
        DF.clear();
    }


    public boolean isDom(DomTreeNode node) {
        while (node != null) {
            if (node.iDom == this) return true;
            node = node.iDom;
        }
        return false;
    }

    @Override
    public String toString() {
        return origin.toString();
    }
}
