package src.utils.error;

import src.utils.Position;

public class SyntaxError extends MxError {
    public SyntaxError(Position pos, String msg) {
        super(pos, "SyntaxError: " + msg);
    }
}
