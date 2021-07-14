package knight_board.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Output {
    private Knight position;
    private final OutputStatus status;

    public Output(final Knight position, final OutputStatus status) {
        this.position = position;
        this.status = status;
    }

    public Output(final OutputStatus status) {
        this.status = status;
    }

    public Knight getPosition() {
        return position;
    }

    public OutputStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Output output = (Output) o;
        return Objects.equal(position, output.position) && status == output.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position, status);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("position", position)
                .add("status", status)
                .toString();
    }
}
