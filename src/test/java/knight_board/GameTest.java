package knight_board;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.Arrays;
import java.util.Collections;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import knight_board.exceptions.BoardDefinitionException;
import knight_board.exceptions.CommandInitializationException;
import knight_board.exceptions.CommandsApiException;
import knight_board.exceptions.KnightInitializationException;
import knight_board.exceptions.OutOfBoardException;
import knight_board.model.Board;
import knight_board.model.Command;
import knight_board.model.Commands;
import knight_board.model.Coordinates;
import knight_board.model.Direction;
import knight_board.model.Knight;
import knight_board.model.Output;
import knight_board.model.OutputStatus;
import knight_board.service.GameService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Game.class, Command.class})
public class GameTest {

    @Mock
    private GameService gameservice;

    @Mock
    private ObjectMapper mapperOutput;

    @Mock
    private Output output;

    @InjectMocks
    private Game game;

    @Test
    public void shouldPrintOutputStatusAsGenericErrorThrowingBoardDefinitionException() throws JsonProcessingException {
        when(gameservice.boardDefinition()).thenThrow(new BoardDefinitionException("I fire for test"));
        game.start();

        assertEquals(game.getOutput(), new Output(OutputStatus.GENERIC_ERROR));
    }

    @Test
    public void shouldPrintOutputStatusAsGenericErrorThrowingCommandsApiException() throws JsonProcessingException {
        when(gameservice.boardDefinition()).thenReturn(new Board(0, 0, emptyList()));
        when(gameservice.commands()).thenThrow(new CommandsApiException("I fire for test"));
        game.start();

        assertEquals(game.getOutput(), new Output(OutputStatus.GENERIC_ERROR));
    }

    @Test
    public void shouldPrintOutputStatusAsInvalidStartPositionThrowingKnightInitializationException() throws Exception {
        when(gameservice.boardDefinition()).thenReturn(new Board(0, 0, emptyList()));
        when(gameservice.commands()).thenReturn(new Commands(Collections.singletonList("START -1,-2,NORTH")));
        whenNew(Knight.class).withArguments(any(Coordinates.class),
                any(Direction.class),
                any(Board.class)).thenThrow(new KnightInitializationException("I fire for test"));

        game.start();

        assertEquals(game.getOutput(), new Output(OutputStatus.INVALID_START_POSITION));
    }

    @Test
    public void shouldPrintOutputStatusAsGenericErrorThrowingCommandInitializationException() throws Exception {
        when(gameservice.boardDefinition()).thenReturn(new Board(0, 0, emptyList()));
        when(gameservice.commands()).thenReturn(new Commands(Collections.singletonList("START -1,-2,NORTH")));
        PowerMockito.mockStatic( Command.class );
        when(Command.of(anyString())).thenThrow(new CommandInitializationException("I fire for test"));

        game.start();

        assertEquals(game.getOutput(), new Output(OutputStatus.GENERIC_ERROR));
    }

    @Test
    public void shouldPrintOutputStatusAsOutOfTheBoardThrowingOutOfBoardException() throws Exception {
        final Board testBoard = new Board(1, 1, Collections.singletonList(new Coordinates(1, 0)));
        final Knight mockKnight = Mockito.mock(Knight.class);
        when(gameservice.boardDefinition()).thenReturn(testBoard);
        when(gameservice.commands()).thenReturn(new Commands(Arrays.asList("START 0,0,NORTH", "MOVE 7")));
        whenNew(Knight.class).withArguments(any(Coordinates.class),
                any(Direction.class),
                any(Board.class)).thenReturn(mockKnight);
        doThrow(new OutOfBoardException("I fire for test")).when(mockKnight).move(anyInt(), any(Board.class));

        game.start();

        assertEquals(game.getOutput(), new Output(OutputStatus.OUT_OF_THE_BOARD));
    }

    @Test
    public void shouldPrintOutputStatusAsSuccess() throws Exception {
        final Board testBoard = new Board(1, 1, Collections.singletonList(new Coordinates(1, 0)));
        final Knight mockKnight = Mockito.mock(Knight.class);
        when(gameservice.boardDefinition()).thenReturn(testBoard);
        when(gameservice.commands()).thenReturn(new Commands(Arrays.asList("START 0,0,NORTH", "MOVE 7")));
        whenNew(Knight.class).withArguments(any(Coordinates.class),
                any(Direction.class),
                any(Board.class)).thenReturn(mockKnight);

        game.start();

        assertEquals(game.getOutput(), new Output(mockKnight, OutputStatus.SUCCESS));
    }
}