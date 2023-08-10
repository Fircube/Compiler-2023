package src.utils.scope;

public class LoopScope extends Scope{
    public boolean isLoop = true;

    public LoopScope(Scope parentScope){
        super(parentScope);
    }
}
