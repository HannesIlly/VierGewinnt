package model.action;

import java.util.Objects;

/**
 * Represents the action of a new player joining the game.
 *
 * @author Hannes Illy
 */
public class NewPlayerAction extends Action {

    /**
     * The name of the player.
     */
    private final String name;

    /**
     * Creates a new-player-action with the given player name.
     *
     * @param name The name of the new player.
     */
    public NewPlayerAction(String name) {
        super(Action.TYPE_NEW_PLAYER);
        this.name = name;
    }

    /**
     * Gets the name of the player joining the game.
     *
     * @return The name of the player.
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return super.toString() + ", name=" + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewPlayerAction that = (NewPlayerAction) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
