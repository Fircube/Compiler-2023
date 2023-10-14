package src.backend;

import src.asm.operand.PhysReg;
import src.asm.operand.Reg;
import src.backend.utils.Edge;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class ConflictGraph {

    public HashSet<Edge> adjSet = new LinkedHashSet<>();

    public void init() {
        adjSet.clear();
    }

    public void addEdge(Reg u, Reg v) {
        if (u == v) return;
        var edge1 = new Edge(u, v);
        var edge2 = new Edge(v, u);
        if (!adjSet.contains(edge1)) {
            adjSet.add(edge1);
            adjSet.add(edge2);
            if (!(u instanceof PhysReg)) {
                u.cgNode.adjList.add(v);
                ++u.cgNode.degree;
            }
            if (!(v instanceof PhysReg)) {
                v.cgNode.adjList.add(u);
                ++v.cgNode.degree;
            }
        }
    }

    public void addSpilledEdge(Reg u, Reg v) {
        if (u == v)
            return;
        var edge1 = new Edge(u, v);
        if (!adjSet.contains(edge1)) {
            var edge2 = new Edge(v, u);
            adjSet.add(edge1);
            adjSet.add(edge2);
            u.spilledNode.adjList.add(v);
            u.spilledNode.degree++;
            v.spilledNode.adjList.add(u);
            v.spilledNode.degree++;
        }
    }
}
