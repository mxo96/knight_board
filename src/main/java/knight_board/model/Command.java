package knight_board.model;

import static java.lang.String.format;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import knight_board.exceptions.CommandInitializationException;

public class Command {
    private CommandType type;
    private Coordinates coordinates;
    private Direction direction;
    private int moveSpace;

    private Command(){}

    public Command(String command) throws CommandInitializationException {
        try
        {
            if (command.startsWith("START")) {
                this.type = CommandType.START;
                final String[] values = command.split(" ")[1].split(",");
                this.coordinates = new Coordinates(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                this.direction = Direction.valueOf(values[2]);
            }
            else if (command.startsWith("ROTATE")) {
                this.type = CommandType.ROTATE;
                this.direction = Direction.valueOf(command.split(" ")[1]);
            }
            else if (command.startsWith("MOVE")) {
                this.type = CommandType.MOVE;
                this.moveSpace = Integer.parseInt(command.split(" ")[1]);
            }
            else {
                throw new CommandInitializationException();
            }
        } catch (IllegalArgumentException e) {
            throw new CommandInitializationException(format("IllegalArgument occurred parsing command [command=%s]", command), e);
        } catch (CommandInitializationException e) {
            throw new CommandInitializationException(format("Command not allowed (valid command: START, ROTATE, MOVE) [command=%s]", command), e);
        } catch (Exception e) {
            throw new CommandInitializationException(format("Problem occurred parsing command [command=%s]", command), e);
        }
    }

    public CommandType getType() {
        return type;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getMoveSpace() {
        return moveSpace;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Command command = (Command) o;
        return moveSpace == command.moveSpace && type == command.type && Objects.equal(coordinates, command.coordinates) && direction == command.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, coordinates, direction, moveSpace);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("coordinates", coordinates)
                .add("direction", direction)
                .add("moveSpace", moveSpace)
                .toString();
    }
}
