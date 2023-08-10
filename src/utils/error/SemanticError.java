package src.utils.error;

import src.utils.Position;

public class SemanticError extends MxError {
    public SemanticError(Position pos, String msg) {
        super(pos, "SyntaxError: " + msg);
    }
}
