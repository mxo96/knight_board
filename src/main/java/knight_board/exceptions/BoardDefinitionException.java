package knight_board.exceptions;

public class BoardDefinitionException extends RuntimeException {
    public BoardDefinitionException(final String string) {
        super(string);
    }

    public BoardDefinitionException(final String string, final Throwable cause) {
        super(string);
    }

}
