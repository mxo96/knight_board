package knight_board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import knight_board.service.GameServiceImpl;

public class App {
    public static void main(final String[] args) throws JsonProcessingException {
        final Game game = new Game(new ObjectMapper(), new GameServiceImpl());
        game.start();
    }
}
