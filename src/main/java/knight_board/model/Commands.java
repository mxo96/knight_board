package knight_board.model;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Commands {
    private List<String> commands;

    public Commands() {
    }

    public Commands(final List<String> commands) {
        this.commands = commands;
    }

    public List<String> getCommands() {
        return commands;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Commands commands1 = (Commands) o;
        return Objects.equal(commands, commands1.commands);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(commands);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("commands", commands)
                .toString();
    }
}
