package src.utils.error;

import src.utils.Position;

abstract public class MxError extends RuntimeException{
    private final Position pos;
    private final String msg;

    public MxError(Position pos,String msg){
        this.pos=pos;
        this.msg=msg;
    }

    public String toString() {
        return msg + ": " + pos.toString();
    }
}
