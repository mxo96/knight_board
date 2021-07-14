package knight_board.model;

import static java.lang.String.format;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import knight_board.exceptions.KnightInitializationException;
import knight_board.exceptions.OutOfBoardException;

public class Knight {
    private Coordinates coordinates;
    private Direction direction;

    public Knight(final Coordinates coordinates, final Direction direction, final Board board) throws KnightInitializationException {
        validateStartPosition(coordinates, board);
        this.coordinates = coordinates;
        this.direction = direction;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(final Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    //TODO check use streams
    private void validateStartPosition(final Coordinates knightCoordinates, final Board board) throws KnightInitializationException {
        if (knightCoordinates.getX() < 0 || knightCoordinates.getY() < 0) {
            throw new KnightInitializationException(format("Invalid Knight start position out of board[xKnight=%s, yKnight=%s]", knightCoordinates.getX(), knightCoordinates.getY()));
        } else if (knightCoordinates.getX() > board.getWidth()) {
            throw new KnightInitializationException(format("Invalid Knight start position out of board[xKnight=%s, widthBoard=%s]", knightCoordinates.getX(), board.getWidth()));
        } else if (knightCoordinates.getY() > board.getHeight()) {
            throw new KnightInitializationException(format("Invalid Knight start position out of board[yKnight=%s, heightBoard=%s]", knightCoordinates.getY(), board.getHeight()));
        }
        for (final Coordinates obstacleCoordinates : board.getObstacles())
            if (knightCoordinates.equals(obstacleCoordinates)) {
                throw new KnightInitializationException(format("Invalid Knight start position overlaps with obstacles[knightCoordinates=%s, obstacleCoordinates=%s]", knightCoordinates, obstacleCoordinates));
            }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Knight knight = (Knight) o;
        return Objects.equal(coordinates, knight.coordinates) && direction == knight.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinates, direction);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("coordinates", coordinates)
                .add("direction", direction)
                .toString();
    }

    public void move(final int moveSpaceValue, final Board board) throws OutOfBoardException {
        boolean obstacleFound = false;
        for (int i = 1; i <= moveSpaceValue; i++) {
            if (this.direction == Direction.NORTH) {
                for (final Coordinates obstacleCoordinates : board.getObstacles()) {
                    if (this.coordinates.getX() == obstacleCoordinates.getX() && (this.getCoordinates().getY() + 1) == obstacleCoordinates.getY()) {
                        obstacleFound = true;
                        i = moveSpaceValue + 1;
                        break;
                    } else if (this.getCoordinates().getY() + 1 > board.getHeight()) {
                        throw new OutOfBoardException("uscito dalla board");
                    }
                }
                if (!obstacleFound) {
                    this.setCoordinates(new Coordinates(this.getCoordinates().getX(), this.getCoordinates().getY() + 1));
                }
            } else if (this.direction == Direction.SOUTH) {
                for (final Coordinates obstacleCoordinates : board.getObstacles()) {
                    if (this.coordinates.getX() == obstacleCoordinates.getX() && (this.getCoordinates().getY() - 1) == obstacleCoordinates.getY()) {
                        obstacleFound = true;
                        i = moveSpaceValue + 1;
                        break;
                    } else if (this.getCoordinates().getY() - 1 < 0) {
                        throw new OutOfBoardException("uscito dalla board");
                    }
                }
                if (!obstacleFound) {
                    this.setCoordinates(new Coordinates(this.getCoordinates().getX(), this.getCoordinates().getY() - 1));
                }
            } else if (this.direction == Direction.EAST) {
                for (final Coordinates obstacleCoordinates : board.getObstacles()) {
                    if (this.coordinates.getY() == obstacleCoordinates.getY() && (this.getCoordinates().getX() + 1) == obstacleCoordinates.getX()) {
                        obstacleFound = true;
                        i = moveSpaceValue + 1;
                        break;
                    } else if (this.getCoordinates().getX() + 1 > board.getWidth()) {
                        throw new OutOfBoardException("uscito dalla board");
                    }
                }
                if (!obstacleFound) {
                    this.setCoordinates(new Coordinates(this.getCoordinates().getX() + 1, this.getCoordinates().getY()));
                }
            } else if (this.direction == Direction.WEST) {
                for (final Coordinates obstacleCoordinates : board.getObstacles()) {
                    if (this.coordinates.getY() == obstacleCoordinates.getY() && (this.getCoordinates().getX() - 1) == obstacleCoordinates.getX()) {
                        obstacleFound = true;
                        i = moveSpaceValue + 1;
                        break;
                    } else if (this.getCoordinates().getX() - 1 < 0) {
                        throw new OutOfBoardException("uscito dalla board");
                    }

                }
                if (!obstacleFound) {
                    this.setCoordinates(new Coordinates(this.getCoordinates().getX() - 1, this.getCoordinates().getY()));
                }
            }
        }
    }
}
