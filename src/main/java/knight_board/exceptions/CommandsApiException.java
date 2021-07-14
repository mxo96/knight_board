package knight_board.exceptions;

public class CommandsApiException extends RuntimeException {
    public CommandsApiException(final String string) {
        super(string);
    }

    public CommandsApiException(final String string, final Throwable cause) {
        super(string);
    }

}
