package knight_board.exceptions;

public class OutOfBoardException extends Exception {
    public OutOfBoardException(final String string) {
        super(string);
    }

    public OutOfBoardException(final String string, final Throwable cause) {
        super(string);
    }

}
