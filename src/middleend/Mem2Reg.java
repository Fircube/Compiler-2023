package src.middleend;

import src.ir.Block;
import src.ir.Entity;
import src.ir.Function;
import src.ir.IRBuilder;
import src.ir.constant.IntConst;
import src.ir.constant.NullConst;
import src.ir.inst.*;
import src.ir.type.PtrType;
import src.utils.scope.GlobalScope;

import java.util.*;

public class Mem2Reg {
    public GlobalScope globalScope;
    private IRBuilder irBuilder;

    public Mem2Reg(GlobalScope globalScope, IRBuilder irBuilder) {
        this.globalScope = globalScope;
        this.irBuilder = irBuilder;
    }

    public void run() {
        for (String key : globalScope.functions.keySet()) {
            Function func = globalScope.functions.get(key);
            new DomTree().run(func);
            collectAllocas(func);
            for (var alloca : allocas) {
                reservePhi(alloca);
            }
            varRenaming(func.entryBlock);
        }
    }

    ArrayList<Inst> allocas = new ArrayList<>();
    HashMap<PhiInst, String> allocaName = new HashMap<>();
    HashMap<String, Stack<Entity>> nameStack = new HashMap<>();

    void collectAllocas(Function func) {
        allocas.clear();
        for (var inst : func.entryBlock.insts) {
            if (inst instanceof AllocaInst)
                allocas.add(inst);
        }
    }

    void reservePhi(Inst alloca) {
        Queue<Block> queue = new ArrayDeque<>();
        var visited = new HashSet<Block>();
        queue.add(alloca.belonging);
        for (var user : alloca.users) {
            if (user instanceof StoreInst s && s.ptr() == alloca) {
                queue.offer(s.belonging);
            }
        }
        while (!queue.isEmpty()) {
            var node = queue.poll();
            for (var df : node.domTreeNode.DF) {
                if (visited.contains(df.origin)) continue;
                visited.add(df.origin);
                queue.offer(df.origin);
                var phi = new PhiInst(globalScope.phiCnt, ((PtrType) alloca.type).baseType, irBuilder.rename(alloca.name), df.origin);
                globalScope.phiCnt++;
                allocaName.put(phi, alloca.name);
            }
        }
    }

    void varRenaming(Block block) {
        var popList = new ArrayList<String>();
        for (var phi : block.phiInsts) {
            var name = allocaName.get(phi);
            if (name == null) continue;
            updateReplace(name, phi);
            popList.add(name);
        }

        var iter = block.insts.iterator();
        while (iter.hasNext()) {
            var inst = iter.next();
            if (inst instanceof AllocaInst alloca) {
                Entity val;
                if (((PtrType) alloca.type).baseType instanceof PtrType)
                    val = new NullConst();
                else
                    val = new IntConst(0,((PtrType) alloca.type).baseType.size);
                updateReplace(alloca.name, val);
                popList.add(alloca.name);
                iter.remove();
            }
            if (inst instanceof StoreInst store) {
                var ptr = store.ptr();
                if (!allocas.contains(ptr))
                    continue;
                var name = ptr.name;
                updateReplace(name, store.value());
                popList.add(name);
                iter.remove();
            }
            if (inst instanceof LoadInst load) {
                var ptr = load.ptr();
                if (!allocas.contains(ptr))
                    continue;
                var name = ptr.name;
                var replace = getReplace(name);
                inst.replaceUsesTo(replace);
                iter.remove();
            }
        }

        for (var suc : block.nexts) {
            for (var sucPhi : suc.phiInsts) {
                var name = allocaName.get(sucPhi);
                if (name == null) continue;
                sucPhi.addBranch(getReplace(name), block);
            }
        }

        for (var node : block.domTreeNode.children) {
            varRenaming(node.origin);
        }

        for (var name : popList) {
            nameStack.get(name).pop();
        }
    }

    Entity getReplace(String name) {
        var stack = nameStack.get(name);
        if (stack == null || stack.empty()) return null;
        return nameStack.get(name).lastElement();
    }

    void updateReplace(String name, Entity replace) {
        var stack = nameStack.computeIfAbsent(name, k -> new Stack<>());
        stack.push(replace);
    }
}
