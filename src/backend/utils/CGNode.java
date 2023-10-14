package src.backend.utils;

import src.asm.inst.MvInst;
import src.asm.operand.Reg;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class CGNode implements Comparable<CGNode> {
    public HashSet<Reg> adjList = new LinkedHashSet<>();
    public HashSet<MvInst> moveList = new LinkedHashSet<>();
    public int degree;
    public double frequency;
    public Reg origin;
    public Reg color;

    public CGNode(Reg origin) {
        this.origin = origin;
    }

    public void init(boolean precolored) {
        this.adjList.clear();
        this.moveList.clear();
        this.frequency = 0;
        this.degree = precolored ? Integer.MAX_VALUE : 0;
    }

    @Override
    public int compareTo(CGNode o) {
        return Integer.compare(degree, o.degree);
    }
}

