package knight_board.exceptions;

public class CommandsException extends RuntimeException {
    public CommandsException(final String string) {
        super(string);
    }

    public CommandsException(final String string, final Throwable cause) {
        super(string);
    }

}
