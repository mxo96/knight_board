package knight_board.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import knight_board.exceptions.CommandInitializationException;

public class CommandTest {

    @Test
    public void shouldInitializeCommandAsStartType() throws CommandInitializationException {
        final Command command = Command.of("START 1,2,NORTH");

        assertEquals(CommandType.START, command.getType());
        assertEquals(new Coordinates(1,2), command.getCoordinates());
        assertEquals(Direction.NORTH, command.getDirection());

    }

    @Test
    public void shouldInitializeCommandAsRotateType() throws CommandInitializationException {
        final Command command = Command.of("ROTATE NORTH");

        assertEquals(CommandType.ROTATE, command.getType());
        assertEquals(Direction.NORTH, command.getDirection());
    }

    @Test
    public void shouldInitializeCommandAsMoveType() throws CommandInitializationException {
        final Command command = Command.of("MOVE 7");

        assertEquals(CommandType.MOVE, command.getType());
        assertEquals(7, command.getMoveSpace());
    }

    @Test(expected = CommandInitializationException.class)
    public void shouldThrowCommandInitializationExceptionGivenNotRecognizedCommand() throws CommandInitializationException {
        Command.of("NOT-RECOGNIZED command");
    }

    @Test(expected = CommandInitializationException.class)
    public void shouldThrowCommandInitializationExceptionGivenNotWellFormedCommand() throws CommandInitializationException {
        Command.of("START1,2,NORTH");
    }
}