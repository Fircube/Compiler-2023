package src.backend;

import src.asm.ASMFunction;
import src.asm.inst.LoadInst;
import src.asm.inst.MvInst;
import src.asm.inst.StoreInst;
import src.asm.operand.Imm;
import src.asm.operand.PhysReg;
import src.asm.operand.Reg;
import src.asm.operand.VirtReg;
import src.backend.utils.CGNode;
import src.backend.utils.Edge;
import src.utils.scope.GlobalScope;

import java.util.*;

public class RegAllocator {

    public GlobalScope globalScope;

    final HashSet<Reg> precolored = new LinkedHashSet<>(PhysReg.regs.values());
    final HashSet<Reg> initial = new LinkedHashSet<>();
    final HashSet<Reg> simplifyWorklist = new LinkedHashSet<>();
    final HashSet<Reg> freezeWorklist = new LinkedHashSet<>();
    final HashSet<Reg> spillWorklist = new LinkedHashSet<>();
    final HashSet<Reg> spilledNodes = new LinkedHashSet<>();
    final HashSet<Reg> coalescedNodes = new LinkedHashSet<>();
    final HashSet<Reg> coloredNodes = new LinkedHashSet<>();
    final Stack<Reg> selectStack = new Stack<>();


    final HashSet<MvInst> coalescedMoves = new LinkedHashSet<>();
    final HashSet<MvInst> constrainedMoves = new LinkedHashSet<>();
    final HashSet<MvInst> frozenMoves = new LinkedHashSet<>();
    final HashSet<MvInst> worklistMoves = new LinkedHashSet<>();
    final HashSet<MvInst> activeMoves = new LinkedHashSet<>();

    final HashMap<Reg, Reg> alias = new HashMap<>();
    final HashSet<Reg> temp = new HashSet<>();
    ConflictGraph CG = new ConflictGraph();
    final static int K = 27;
    ASMFunction curFunc;

    public RegAllocator(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    public void run() {
        for (var func : globalScope.funcs) {
            temp.clear();
            curFunc = func;
            graphColoring();
            removeMv();
        }
    }

    public void graphColoring() {
        init();
        new LivenessAnalysis().runOnFunc(curFunc);
        build();
        makeWorklist();
        do {
            if (!simplifyWorklist.isEmpty()) simplify();
            else if (!worklistMoves.isEmpty()) coalesce();
            else if (!freezeWorklist.isEmpty()) freeze();
            else if (!spillWorklist.isEmpty()) selectSpill();
        } while (!simplifyWorklist.isEmpty() || !worklistMoves.isEmpty() || !freezeWorklist.isEmpty() || !spillWorklist.isEmpty());
        assignColors();
        if (!spilledNodes.isEmpty()) {
            rewriteProgram();
            graphColoring();
        }
    }

    void init() {
        initial.clear();
        simplifyWorklist.clear();
        freezeWorklist.clear();
        spillWorklist.clear();
        spilledNodes.clear();
        coalescedNodes.clear();
        alias.clear();
        coloredNodes.clear();
        selectStack.clear();

        coalescedMoves.clear();
        constrainedMoves.clear();
        frozenMoves.clear();
        worklistMoves.clear();
        activeMoves.clear();

        CG.init();

        for (var block : curFunc.blocks) {
            for (var inst : block.insts) {
                for (var reg : inst.uses())
                    if (reg instanceof VirtReg)
                        initial.add(reg);
                for (var reg : inst.defs())
                    if (reg instanceof VirtReg)
                        initial.add(reg);
            }
        }

        for (var reg : initial) {
            reg.cgNode.color = null;
            reg.cgNode.init(false);
        }
        for (var reg : precolored) {
            reg.cgNode.color = reg;
            reg.cgNode.init(true);
        }

        for (var block : curFunc.blocks) {
            for (var inst : block.insts) {
                for (var reg : inst.defs())
                    reg.cgNode.frequency += 1;
                for (var reg : inst.uses())
                    reg.cgNode.frequency += 1;
            }
        }
    }

    void removeMv() {
        for (var block : curFunc.blocks) {
            block.insts.removeIf(inst -> inst instanceof MvInst mv && mv.rs1.cgNode.color == mv.rd.cgNode.color);
        }
    }

    void build() {
        for (var block : curFunc.blocks) {
            var live = new HashSet<>(block.liveOut);
            for (int i = block.insts.size() - 1; i >= 0; i--) {
                var inst = block.insts.get(i);
                if (inst instanceof MvInst m) {
                    live.removeAll(inst.uses());
                    for (var u : inst.uses()) {
                        u.cgNode.moveList.add(m);
                    }
                    for (var d : inst.defs()) {
                        d.cgNode.moveList.add(m);
                    }
                    worklistMoves.add(m);
                }

                live.addAll(inst.defs());
                for (var def : inst.defs()) {
                    for (var l : live) {
                        CG.addEdge(l, def);
                    }
                }
                live.removeAll(inst.defs());
                live.addAll(inst.uses());
            }
        }
    }

    void makeWorklist() {
        for (var n : initial) {
            if (n.cgNode.degree >= K) {
                spillWorklist.add(n);
            } else if (moveRelated(n)) {
                freezeWorklist.add(n);
            } else {
                simplifyWorklist.add(n);
            }
        }
        initial.clear();
    }

    HashSet<Reg> adjacent(Reg reg) {
        var tmp = new HashSet<>(reg.cgNode.adjList);
        tmp.removeAll(selectStack);
        tmp.removeAll(coloredNodes);
        return tmp;
    }

    HashSet<MvInst> nodeMoves(Reg reg) {
        var tmp = new HashSet<MvInst>();
        for (var m : reg.cgNode.moveList) {
            if (activeMoves.contains(m)) tmp.add(m);
            else if (worklistMoves.contains(m)) tmp.add(m);
        }
        return tmp;
    }

    boolean moveRelated(Reg reg) {
        return !nodeMoves(reg).isEmpty();
    }

    void simplify() {
        var iter = simplifyWorklist.iterator();
        var reg = iter.next();
        iter.remove();
        selectStack.push(reg);
        for (var m : adjacent(reg)) {
            decrementDegree(m);
        }
    }

    void decrementDegree(Reg reg) {
        int d = reg.cgNode.degree;
        --reg.cgNode.degree;
        if (d == K) {
            var adj = adjacent(reg);
            adj.add(reg);
            enableMoves(adj);
            spillWorklist.remove(reg);
            if (moveRelated(reg)) {
                freezeWorklist.add(reg);
            } else {
                simplifyWorklist.add(reg);
            }
        }
    }

    void enableMoves(Set<Reg> regs) {
        for (Reg reg : regs) {
            var moves = nodeMoves(reg);
            for (var m : moves)
                if (activeMoves.contains(m)) {
                    activeMoves.remove(m);
                    worklistMoves.add(m);
                }
        }
    }

    void coalesce() {
        var iter = worklistMoves.iterator();
        var mv = iter.next();

        var u = getAlias(mv.rd);
        var v = getAlias(mv.rs1);
        if (v instanceof PhysReg) {
            var tmp = u;
            u = v;
            v = tmp;
        }
        var edge = new Edge(u, v);
        iter.remove();

        if (u == v) {
            coalescedMoves.add(mv);
            addWorklist(u);
        } else if (v instanceof PhysReg || CG.adjSet.contains(edge)) {
            constrainedMoves.add(mv);
            addWorklist(u);
            addWorklist(v);
        } else if (u instanceof PhysReg && george(u, v) || !(u instanceof PhysReg) && briggs(u, v)) {
            coalescedMoves.add(mv);
            combine(u, v);
            addWorklist(u);
        } else {
            activeMoves.add(mv);
        }
    }

    void addWorklist(Reg reg) {
        if (!(reg instanceof PhysReg) && !moveRelated(reg) && reg.cgNode.degree < K) {
            freezeWorklist.remove(reg);
            simplifyWorklist.add(reg);
        }
    }

    Reg getAlias(Reg reg) {
        if (!coalescedNodes.contains(reg)) return reg;
        var a = getAlias(alias.get(reg));
        alias.put(reg, a);
        return a;
    }

    void combine(Reg u, Reg v) {
        if (freezeWorklist.contains(v)) {
            freezeWorklist.remove(v);
        } else {
            spillWorklist.remove(v);
        }
        coalescedNodes.add(v);
        alias.put(v, u);
        u.cgNode.moveList.addAll(v.cgNode.moveList);
        enableMoves(Set.of(v));
        for (var t : adjacent(v)) {
            CG.addEdge(t, u);
            decrementDegree(t);
        }
        if (u.cgNode.degree >= K && freezeWorklist.contains(u)) {
            freezeWorklist.remove(u);
            spillWorklist.add(u);
        }
    }

    void freeze() {
        var iter = freezeWorklist.iterator();
        var reg = iter.next();
        iter.remove();
        simplifyWorklist.add(reg);
        freezeMoves(reg);
    }

    void freezeMoves(Reg u) {
        for (var mv : nodeMoves(u)) {
            Reg v = getAlias(mv.rs1);
            if (getAlias(u) == v) v = getAlias(mv.rd);
            activeMoves.remove(mv);
            frozenMoves.add(mv);
            if (nodeMoves(v).isEmpty() && v.cgNode.degree < K) {
                freezeWorklist.remove(v);
                simplifyWorklist.add(v);
            }
        }
    }

    void selectSpill() {
        Reg m = null;
        double minCost = Double.POSITIVE_INFINITY;
        for (var reg : spillWorklist) {
            double regCost = reg.cgNode.frequency / reg.cgNode.degree;
            if (temp.contains(reg)) regCost += 1e10;
            if (regCost < minCost) {
                m = reg;
                minCost = regCost;
            }
        }
        spillWorklist.remove(m);
        simplifyWorklist.add(m);
        freezeMoves(m);
    }

    void assignColors() {
        while (!selectStack.empty()) {
            var reg = selectStack.pop();
            var okColor = new ArrayList<>();
            for (int i = 0; i < 7; ++i) {
                okColor.add(PhysReg.regs.get("t" + i));
            }
            for (int i = 0; i < 8; ++i) {
                okColor.add(PhysReg.regs.get("a" + i));
            }
            for (int i = 0; i < 12; ++i) {
                okColor.add(PhysReg.regs.get("s" + i));
            }
            for (var w : reg.cgNode.adjList) {
                w = getAlias(w);
                if (w instanceof PhysReg || coloredNodes.contains(w))
                    okColor.remove(w.cgNode.color);
            }
            if (okColor.isEmpty()) {
                spilledNodes.add(reg);
            } else {
                coloredNodes.add(reg);
                reg.cgNode.color = (Reg) okColor.iterator().next();
            }
        }

        for (var reg : coalescedNodes) {
            reg.cgNode.color = getAlias(reg).cgNode.color;
        }
    }

    void rewriteProgram() {
        CG.init();
        for (var node : spilledNodes) {
            node.spilledNode.init(false);
        }
        for (var block : curFunc.blocks) {
            var lives = filterSpilled(new HashSet<>(block.liveOut));
            for (int i = block.insts.size() - 1; i >= 0; i--) {
                var inst = block.insts.get(i);
                var uses = filterSpilled(inst.uses());
                var defs = filterSpilled(inst.defs());
                if (inst instanceof MvInst m) {
                    lives.removeAll(uses);
                    for (var u : uses) {
                        u.spilledNode.moveList.add(m);
                    }
                    for (var d : defs) {
                        d.spilledNode.moveList.add(m);
                    }
                }

                lives.addAll(defs);
                for (var def : defs) {
                    for (var live : lives) {
                        CG.addSpilledEdge(live, def);
                    }
                }
                lives.removeAll(defs);
                lives.addAll(uses);
            }
        }

        int i = 0, num = spilledNodes.size();
        CGNode[] nodes = new CGNode[num];
        for (var reg : spilledNodes) {
            nodes[i++] = reg.spilledNode;
        }
        var colors = new HashSet<Reg>();
        i = 0;
        while (i != num) {
            Arrays.sort(nodes, i, num);
            var node = nodes[i++];
            var reg = node.origin;
            var availColors = new HashSet<>(colors);
            for (var t : node.adjList) {
                t = getAlias(t);
                availColors.remove(t.cgNode.color);
                t.spilledNode.degree--;
            }
            if (availColors.isEmpty()) {
                colors.add(reg);
                reg.cgNode.color = reg;
            } else {
                reg.cgNode.color = availColors.iterator().next();
            }
        }
        for (var reg : colors) {
            reg.stackOffset = new Imm(curFunc.spilledCnt, true, Imm.Type.spill);
            curFunc.spilledCnt++;
        }

        for (var block : curFunc.blocks) {
            var iter = block.insts.listIterator();
            while (iter.hasNext()) {
                var inst = iter.next();
                iter.previous();
                for (var reg : inst.uses()) {
                    var regAlias = getAlias(reg);

                    if (!spilledNodes.contains(regAlias)) {
                        if (regAlias != reg)
                            inst.replaceUse(reg, regAlias);
                        continue;
                    }
                    var tmp = new VirtReg(((VirtReg) reg).size);
                    var load = new LoadInst(tmp.size, tmp, PhysReg.regs.get("sp"), regAlias.cgNode.color.stackOffset, null);
                    iter.add(load);
                    inst.replaceUse(reg, tmp);
                    temp.add(tmp);
                }
                iter.next();
                for (var reg : inst.defs()) {
                    var regAlias = getAlias(reg);
                    if (!spilledNodes.contains(regAlias)) {
                        if (regAlias != reg)
                            inst.replaceDef(reg, regAlias);
                        continue;
                    }
                    var tmp = new VirtReg(((VirtReg) reg).size);
                    var store = new StoreInst(tmp.size, tmp, PhysReg.regs.get("sp"), regAlias.cgNode.color.stackOffset, null);
                    iter.add(store);
                    inst.replaceDef(reg, tmp);
                    temp.add(tmp);
                }
            }
        }
    }

    HashSet<Reg> filterSpilled(HashSet<Reg> set) {
        set.retainAll(spilledNodes);
        return set;
    }

    boolean george(Reg u, Reg v) {
        for (var t : adjacent(v)) {
            if (t.cgNode.degree < K) continue;
            if (t instanceof PhysReg) continue;
            if (CG.adjSet.contains(new Edge(t, u))) continue;
            return false;
        }
        return true;
    }

    boolean briggs(Reg u, Reg v) {
        var adj = adjacent(u);
        adj.addAll(adjacent(v));
        int k = 0;
        for (var n : adj) {
            if (n.cgNode.degree >= K) ++k;
        }
        return k < K;
    }
}