package src.backend.utils;

import src.asm.ASMBlock;

public class LANode implements Comparable<LANode> {
    public ASMBlock origin;
    public int dfn;

    public LANode(int dfn, ASMBlock origin) {
        this.dfn = dfn;
        this.origin = origin;
    }

    @Override
    public int compareTo(LANode t) {
        return Integer.compare(this.dfn, t.dfn);
    }
}
