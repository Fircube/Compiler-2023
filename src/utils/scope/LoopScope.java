package src.utils.scope;

import src.ir.Block;

public class LoopScope extends Scope {
    public Block continueBlock, breakBlock;

    public LoopScope(Scope parentScope) {
        super(parentScope);
    }
}
