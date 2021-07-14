package knight_board.service;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;
import knight_board.exceptions.BoardDefinitionException;
import knight_board.exceptions.CommandsApiException;
import knight_board.model.Board;
import knight_board.model.Commands;
import knight_board.model.Coordinates;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameServiceImpl.class)
public class GameServiceImplTest extends TestCase {
    @Mock
    private URL url;

    @Mock
    private HttpURLConnection connection;

    @InjectMocks
    private GameServiceImpl sut;

    @Test
    public void shouldReturnABoard() throws Exception {
        final ByteArrayInputStream mockedResponse = new ByteArrayInputStream("{\"width\":1,\"height\":1,\"obstacles\":[{\"x\":0,\"y\":1}]}".getBytes());

        whenNew(URL.class).withArguments(anyString()).thenReturn(url);
        when(url.openConnection()).thenReturn(connection);
        when(connection.getInputStream()).thenReturn(mockedResponse);

        final Board board = sut.boardDefinition();

        final List<Coordinates> coordinates = Collections.singletonList(new Coordinates(0, 1));
        final Board expectedBoard = new Board(1, 1, coordinates);

        assertEquals(board, expectedBoard);
    }

    @Test(expected = BoardDefinitionException.class)
    public void shouldThrowBoardDefinitionExceptionGivenMalformedApiUrl() throws Exception {
        PowerMockito.mockStatic(System.class);
        when(System.getenv(anyString())).thenReturn("://url-without-protocol");

        sut.boardDefinition();
    }

    @Test(expected = BoardDefinitionException.class)
    public void shouldThrowBoardDefinitionExceptionGivenMalformedJson() throws Exception {
        final ByteArrayInputStream mockedResponse = new ByteArrayInputStream("{\"width\"1}".getBytes());

        whenNew(URL.class).withArguments(anyString()).thenReturn(url);
        when(url.openConnection()).thenReturn(connection);
        when(connection.getInputStream()).thenReturn(mockedResponse);

        sut.boardDefinition();
    }

    @Test
    public void shouldReturnCommands() throws Exception {
        final ByteArrayInputStream mockedResponse = new ByteArrayInputStream("{\"commands\":[\"command\"]}".getBytes());

        whenNew(URL.class).withArguments(anyString()).thenReturn(url);
        when(url.openConnection()).thenReturn(connection);
        when(connection.getInputStream()).thenReturn(mockedResponse);

        final Commands actualCommands = sut.commands();

        final List<String> commands = Collections.singletonList("command");
        final Commands expectedCommands = new Commands(commands);

        assertEquals(actualCommands, expectedCommands);
    }

    @Test(expected = CommandsApiException.class)
    public void shouldThrowCommandsApiExceptionGivenMalformedApiUrl() throws Exception {
        PowerMockito.mockStatic(System.class);
        when(System.getenv(anyString())).thenReturn("://url-without-protocol");

        sut.commands();
    }

    @Test(expected = CommandsApiException.class)
    public void shouldThrowCommandsApiExceptionGivenMalformedJson() throws Exception {
        final ByteArrayInputStream mockedResponse = new ByteArrayInputStream("{\"commands\"[\"command\"]}".getBytes());

        whenNew(URL.class).withArguments(anyString()).thenReturn(url);
        when(url.openConnection()).thenReturn(connection);
        when(connection.getInputStream()).thenReturn(mockedResponse);

        sut.commands();
    }
}