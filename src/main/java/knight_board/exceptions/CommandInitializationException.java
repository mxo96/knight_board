package knight_board.exceptions;

public class CommandInitializationException extends Exception{
    public CommandInitializationException(final String string) {
        super(string);
    }
    public CommandInitializationException() {
        super();
    }
    public CommandInitializationException(final String string, final Throwable cause) {
        super(string);
    }

}
