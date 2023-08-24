package src.utils.error;

import src.utils.Position;

public class IRError extends MxError {
    public IRError(Position pos, String msg) {
        super(pos, "IRError: " + msg);
    }
}
