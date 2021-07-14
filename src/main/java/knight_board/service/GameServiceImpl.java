package knight_board.service;

import static java.lang.String.format;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import knight_board.exceptions.BoardDefinitionException;
import knight_board.exceptions.CommandsException;
import knight_board.model.Board;
import knight_board.model.Commands;

public class GameServiceImpl implements GameService {

    private static final String BOARD_API = System.getenv("BOARD_API");
    private static final String COMMANDS_API = System.getenv("COMMANDS_API");
    private final ObjectMapper mapper = new ObjectMapper();

    //TODO check if its better to manage the various exception
    @Override
    public Board boardDefinition() throws BoardDefinitionException {
        try {
            final URL url = new URL(BOARD_API);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            final InputStream responseStream = connection.getInputStream();

            return mapper.readValue(responseStream, Board.class);
        } catch (final Exception e) {
            throw new BoardDefinitionException(format("There was a problem with the Board API [boardApi=%s]", BOARD_API), e);
        }
    }

    //TODO check if its better to manage the various exception
    @Override
    public Commands commands() throws CommandsException {
        try {
            final URL url = new URL(COMMANDS_API);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            final InputStream responseStream = connection.getInputStream();

            return mapper.readValue(responseStream, Commands.class);
        } catch (final Exception e) {
            throw new CommandsException(format("There was a problem with the Commands API [commandsApi=%s]", COMMANDS_API), e);
        }
    }
}
