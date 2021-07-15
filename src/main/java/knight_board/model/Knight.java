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

    private void validateStartPosition(final Coordinates knightCoordinates, final Board board) throws KnightInitializationException {
        if (knightCoordinates.getX() < 0 || knightCoordinates.getY() < 0) {
            throw new KnightInitializationException(format("Invalid Knight start position out of board[xKnight=%s, yKnight=%s]", knightCoordinates.getX(), knightCoordinates.getY()));
        } else if (knightCoordinates.getX() > board.getWidth()) {
            throw new KnightInitializationException(format("Invalid Knight start position out of board[xKnight=%s, widthBoard=%s]", knightCoordinates.getX(), board.getWidth()));
        } else if (knightCoordinates.getY() > board.getHeight()) {
            throw new KnightInitializationException(format("Invalid Knight start position out of board[yKnight=%s, heightBoard=%s]", knightCoordinates.getY(), board.getHeight()));
        }

        final boolean obstacleFound = board.getObstacles()
                .stream()
                .anyMatch(knightCoordinates::equals);
        if (obstacleFound) {
            throw new KnightInitializationException(format("Invalid Knight start position overlaps with obstacles[knightCoordinates=%s]", knightCoordinates));
        }
    }

    public void move(final int moveSpaceValue, final Board board) throws OutOfBoardException {
        for (int i = 1; i <= moveSpaceValue; i++) {
            final Coordinates newKnightCoordinates = new Coordinates();
            switch (getDirection()){
                case NORTH:
                    newKnightCoordinates.setX(getCoordinates().getX());
                    newKnightCoordinates.setY(getCoordinates().getY() + 1);
                    break;
                case SOUTH:
                    newKnightCoordinates.setX(getCoordinates().getX());
                    newKnightCoordinates.setY(getCoordinates().getY() - 1);
                    break;
                case EAST:
                    newKnightCoordinates.setX(getCoordinates().getX() + 1);
                    newKnightCoordinates.setY(getCoordinates().getY());
                    break;
                case WEST:
                    newKnightCoordinates.setX(getCoordinates().getX() - 1);
                    newKnightCoordinates.setY(getCoordinates().getY());
                    break;
            }
            if (canKnightMove(newKnightCoordinates, board)) {
                this.setCoordinates(newKnightCoordinates);
            }
        }
    }

    private boolean canKnightMove(final Coordinates newKnightCoordinates, final Board board) throws OutOfBoardException {
        outOfBoardMoveValidation(newKnightCoordinates, board);

        return board.getObstacles()
                .stream()
                .noneMatch(newKnightCoordinates::equals);
    }

    private void outOfBoardMoveValidation(final Coordinates newKnightCoordinates, final Board board) throws OutOfBoardException {
        switch(getDirection()){
            case NORTH:
                if (newKnightCoordinates.getY() > board.getHeight()) {
                    throw new OutOfBoardException(format("Knight out of the Board [direction=%s]", getDirection()));
                }
                break;
            case SOUTH:
                if (newKnightCoordinates.getY() < 0) {
                    throw new OutOfBoardException(format("Knight out of the Board [direction=%s]", getDirection()));
                }
                break;
            case EAST:
                if (newKnightCoordinates.getX() > board.getWidth()) {
                    throw new OutOfBoardException(format("Knight out of the Board [direction=%s]", getDirection()));
                }
                break;
            case WEST:
                if (newKnightCoordinates.getX() < 0) {
                    throw new OutOfBoardException(format("Knight out of the Board [direction=%s]", getDirection()));
                }
                break;
        }
    }
}
