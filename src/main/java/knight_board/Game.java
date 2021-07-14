package knight_board;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import knight_board.exceptions.BoardDefinitionException;
import knight_board.exceptions.CommandsException;
import knight_board.exceptions.KnightInitializationException;
import knight_board.exceptions.OutOfBoardException;
import knight_board.model.Board;
import knight_board.model.Commands;
import knight_board.model.Coordinates;
import knight_board.model.Direction;
import knight_board.model.Knight;
import knight_board.model.Output;
import knight_board.model.OutputStatus;
import knight_board.service.GameService;

public class Game {
    private static final Logger LOGGER = Logger.getLogger(Game.class);

    private final ObjectMapper mapperOutput;
    private final GameService gameservice;
    private Output output;
    private Knight knight;

    public Game(final ObjectMapper mapperOutput, final GameService gameservice) {
        this.mapperOutput = mapperOutput;
        this.gameservice = gameservice;
    }

    public void start() throws JsonProcessingException {
        try {
            final Board board = gameservice.boardDefinition();
            final Commands commands = gameservice.commands();

            for (final String command : commands.getCommands()) {
                if (command.startsWith("START")) {
                    final String[] value = command.split(" ");
                    final String[] knightValue = value[1].split(",");
                    knight = new Knight(new Coordinates(Integer.parseInt(knightValue[0]),
                            Integer.parseInt(knightValue[1])),
                            Direction.valueOf(knightValue[2]),
                            board);
                } else if (command.startsWith("ROTATE")) {
                    final String directionValue = command.split(" ")[1];
                    knight.setDirection(Direction.valueOf(directionValue));
                } else if (command.startsWith("MOVE")) {
                    final int moveSpaceValue = Integer.parseInt(command.split(" ")[1]);
                    knight.move(moveSpaceValue, board);
                }
            }
            LOGGER.debug("Knight arrived to destination");
            output = new Output(knight, OutputStatus.SUCCESS);
        } catch (final BoardDefinitionException | CommandsException e) {
            output = new Output(OutputStatus.GENERIC_ERROR);
            LOGGER.error("Problem calling Board API", e);
        } catch (final KnightInitializationException e) {
            output = new Output(OutputStatus.INVALID_START_POSITION);
            LOGGER.error("Invalid start position", e);
        } catch (final OutOfBoardException e) {
            output = new Output(OutputStatus.OUT_OF_THE_BOARD);
            LOGGER.error("Knight out of board", e);
        } finally {
            final String json = mapperOutput.writeValueAsString(output);
            System.out.println(json);
        }
    }

    public Output getOutput() {
        return output;
    }
}
