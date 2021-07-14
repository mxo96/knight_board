package knight_board.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import knight_board.exceptions.KnightInitializationException;
import knight_board.exceptions.OutOfBoardException;

public class KnightTest {

    private Coordinates coordinatesKnight;

    @Before
    public void setUp() {
        coordinatesKnight = new Coordinates(2, 2);
    }

    @Test
    public void shouldInitializeKnight() throws KnightInitializationException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 1), new Coordinates(2, 1));
        final Board board = new Board(2, 2, obstacles);
        new Knight(coordinatesKnight, Direction.NORTH, board);
    }

    @Test(expected = KnightInitializationException.class)
    public void shouldNotInitializeKnightAndThrowKnightInitializationExceptionGivenKnightXCoordinatesGreaterThenBoardWidth() throws KnightInitializationException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 1), new Coordinates(1, 1));
        final Board board = new Board(1, 2, obstacles);
        new Knight(coordinatesKnight, Direction.NORTH, board);
    }

    @Test(expected = KnightInitializationException.class)
    public void shouldNotInitializeKnightAndThrowKnightInitializationExceptionGivenKnightYCoordinatesGreaterThenBoardHeight() throws KnightInitializationException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 1), new Coordinates(1, 1));
        final Board board = new Board(2, 1, obstacles);
        new Knight(coordinatesKnight, Direction.NORTH, board);
    }

    @Test(expected = KnightInitializationException.class)
    public void shouldNotInitializeKnightAndThrowKnightInitializationExceptionGivenKnightCoordinatesEqualsToBoardObstacle() throws KnightInitializationException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 1), new Coordinates(2, 2));
        final Board board = new Board(2, 2, obstacles);
        new Knight(coordinatesKnight, Direction.NORTH, board);
    }

    @Test(expected = KnightInitializationException.class)
    public void shouldNotInitializeKnightAndThrowKnightInitializationExceptionGivenKnightCoordinatesWithXOrYNegative() throws KnightInitializationException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 1), new Coordinates(2, 2));
        final Board board = new Board(2, 2, obstacles);
        new Knight(new Coordinates(-1, -1), Direction.NORTH, board);
    }

    @Test
    public void shouldMoveKnightToNorth() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 1), new Coordinates(0, 0));
        final Board board = new Board(3, 3, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.NORTH, board);
        knight.move(1, board);

        assertEquals(3, knight.getCoordinates().getY());
        assertEquals(2, knight.getCoordinates().getX());
    }

    @Test
    public void shouldMoveKnightToNorthWhileItEncounterAnObstacle() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(2, 4), new Coordinates(2, 0));
        final Board board = new Board(4, 4, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.NORTH, board);
        knight.move(7, board);

        assertEquals(3, knight.getCoordinates().getY());
        assertEquals(2, knight.getCoordinates().getX());
    }

    @Test(expected = OutOfBoardException.class)
    public void shouldThrowOutOfBoardExceptionMovingKnightToNorthAfterTheBoardLimit() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(3, 3), new Coordinates(0, 0));
        final Board board = new Board(3, 3, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.NORTH, board);
        knight.move(7, board);
    }

    @Test
    public void shouldMoveKnightToSouth() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 1), new Coordinates(0, 0));
        final Board board = new Board(3, 3, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.SOUTH, board);
        knight.move(1, board);

        assertEquals(1, knight.getCoordinates().getY());
        assertEquals(2, knight.getCoordinates().getX());
    }

    @Test
    public void shouldMoveKnightToSouthWhileItEncounterAnObstacle() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(2, 4), new Coordinates(2, 0));
        final Board board = new Board(4, 4, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.SOUTH, board);
        knight.move(7, board);

        assertEquals(1, knight.getCoordinates().getY());
        assertEquals(2, knight.getCoordinates().getX());
    }

    @Test(expected = OutOfBoardException.class)
    public void shouldThrowOutOfBoardExceptionMovingKnightToSouthAfterTheBoardLimit() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(3, 3), new Coordinates(0, 2));
        final Board board = new Board(3, 3, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.SOUTH, board);
        knight.move(7, board);
    }

    @Test
    public void shouldMoveKnightToEast() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 1), new Coordinates(4, 0));
        final Board board = new Board(4, 3, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.EAST, board);
        knight.move(1, board);

        assertEquals(2, knight.getCoordinates().getY());
        assertEquals(3, knight.getCoordinates().getX());
    }

    @Test
    public void shouldMoveKnightToEastWhileItEncounterAnObstacle() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(2, 4), new Coordinates(5, 2));
        final Board board = new Board(5, 5, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.EAST, board);
        knight.move(7, board);

        assertEquals(2, knight.getCoordinates().getY());
        assertEquals(4, knight.getCoordinates().getX());
    }

    @Test(expected = OutOfBoardException.class)
    public void shouldThrowOutOfBoardExceptionMovingKnightToEastAfterTheBoardLimit() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(3, 3), new Coordinates(0, 2));
        final Board board = new Board(3, 3, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.EAST, board);
        knight.move(7, board);
    }

    @Test
    public void shouldMoveKnightToWest() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 1), new Coordinates(4, 0));
        final Board board = new Board(4, 3, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.WEST, board);
        knight.move(1, board);

        assertEquals(2, knight.getCoordinates().getY());
        assertEquals(1, knight.getCoordinates().getX());
    }

    @Test
    public void shouldMoveKnightToWestWhileItEncounterAnObstacle() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(0, 2), new Coordinates(5, 2));
        final Board board = new Board(5, 5, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.WEST, board);
        knight.move(7, board);

        assertEquals(2, knight.getCoordinates().getY());
        assertEquals(1, knight.getCoordinates().getX());
    }

    @Test(expected = OutOfBoardException.class)
    public void shouldThrowOutOfBoardExceptionMovingKnightToWestAfterTheBoardLimit() throws KnightInitializationException, OutOfBoardException {
        final List<Coordinates> obstacles = Arrays.asList(new Coordinates(3, 3), new Coordinates(3, 2));
        final Board board = new Board(3, 3, obstacles);
        final Knight knight = new Knight(coordinatesKnight, Direction.WEST, board);
        knight.move(7, board);
    }
}