package knight_board.service;

import knight_board.model.Board;
import knight_board.model.Commands;

public interface GameService {
    Board boardDefinition();

    Commands commands();
}
