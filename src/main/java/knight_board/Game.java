package knight_board;

import static java.lang.String.format;

import org.apache.log4j.Logger;

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

            if(!commands.getCommands().get(0).startsWith("START")){
                throw new KnightInitializationException(format("Knight cannot be initialized without a first START command [firstCommand=%s]", commands.getCommands().get(0)));
            }

            for (final String command : commands.getCommands()) {
                final Command commandObj = Command.of(command);
                switch(commandObj.getType()){
                    case START:
                        knight = new Knight(commandObj.getCoordinates(), commandObj.getDirection(), board);
                        break;
                    case ROTATE:
                        knight.setDirection(commandObj.getDirection());
                        break;
                    case MOVE:
                        knight.move(commandObj.getMoveSpace(), board);
                        break;
                }
            }
            LOGGER.debug("Knight arrived to destination");
            output = new Output(knight, OutputStatus.SUCCESS);
        } catch (final BoardDefinitionException e) {
            output = new Output(OutputStatus.GENERIC_ERROR);
            LOGGER.error("Problem calling Board API", e);
        } catch (final CommandsApiException e) {
            output = new Output(OutputStatus.GENERIC_ERROR);
            LOGGER.error("Problem calling Commands API", e);
        } catch (final KnightInitializationException e) {
            output = new Output(OutputStatus.INVALID_START_POSITION);
            LOGGER.error("Invalid start position", e);
        } catch (final OutOfBoardException e) {
            output = new Output(OutputStatus.OUT_OF_THE_BOARD);
            LOGGER.error("Knight out of board", e);
        } catch (final CommandInitializationException e) {
            output = new Output(OutputStatus.GENERIC_ERROR);
            LOGGER.error("Command received error", e);
        } finally {
            final String json = mapperOutput.writeValueAsString(output);
            System.out.println(json);
        }
    }

    public Output getOutput() {
        return output;
    }
}
