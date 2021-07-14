package knight_board.model;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Board {
    private int width;
    private int height;
    private List<Coordinates> obstacles;

    public Board() {
    }

    public Board(final int width, final int height, final List<Coordinates> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Coordinates> getObstacles() {
        return obstacles;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Board board = (Board) o;
        return width == board.width && height == board.height && Objects.equal(obstacles, board.obstacles);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(width, height, obstacles);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("width", width)
                .add("height", height)
                .add("obstacles", obstacles)
                .toString();
    }
}
