package knight_board.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import knight_board.exceptions.CommandInitializationException;

public class CommandTest {

    @Test
    public void shouldInitialiazeCommandAsStartType() throws CommandInitializationException {
        final Command command = new Command("START 1,2,NORTH");
        assertEquals(CommandType.START, command.getType());
        assertEquals(new Coordinates(1,2), command.getCoordinates());
        assertEquals(Direction.NORTH, command.getDirection());

    }

    @Test
    public void shouldInitialiazeCommandAsRotateType() throws CommandInitializationException {
        final Command command = new Command("ROTATE NORTH");
        assertEquals(CommandType.ROTATE, command.getType());
        assertEquals(Direction.NORTH, command.getDirection());
    }

    @Test
    public void shouldInitialiazeCommandAsMoveType() throws CommandInitializationException {
        final Command command = new Command("MOVE 7");
        assertEquals(CommandType.MOVE, command.getType());
        assertEquals(7, command.getMoveSpace());
    }

    @Test(expected = CommandInitializationException.class)
    public void shouldThrowCommandInitializationExceptionGivenNotRecognizedCommand() throws CommandInitializationException {
        new Command("NOT-RECOGNIZED command");
    }

    @Test(expected = CommandInitializationException.class)
    public void shouldThrowCommandInitializationExceptionGivenNotWellFormedCommand() throws CommandInitializationException {
        new Command("START1,2,NORTH");
    }
}