package src.backend.utils;

import src.asm.operand.Reg;

public class Edge {
    public Reg u, v;

    public Edge(Reg u, Reg v) {
        this.u = u;
        this.v = v;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Edge e && (e.u == u && e.v == v);
    }

    @Override
    public int hashCode() {
        return u.hashCode() ^ v.hashCode();
    }
}
