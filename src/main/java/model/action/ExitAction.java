package model.action;

import model.GameController;

import java.util.Objects;

/**
 * Represents the action of a player exiting the game or the server closing.
 *
 * @author Hannes Illy
 */
public class ExitAction extends Action {

    public static final int PLAYER_EXIT = 0;
    public static final int SERVER_CLOSED = 1;

    /**
     * The name of the player/server exiting the game/server.
     */
    private final String name;
    /**
     * If a player exits the game or the server closes..
     */
    private final int exitType;

    /**
     * Creates a exit-action.
     *
     * @param name The name of the player/server exiting the game.
     */
    public ExitAction(String name, int exitType) {
        super(Action.TYPE_EXIT);
        this.name = name;
        this.exitType = exitType;
    }

    /**
     * Creates a exit-action, where a player exits the game.
     *
     * @param name The name of the player exiting the game.
     */
    public ExitAction(String name) {
        this(name, PLAYER_EXIT);
    }

    /**
     * Gets the name of the player exiting the game.
     *
     * @return The name of the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the exit type of this action. Can be compared to the constants in this class.
     *
     * @return The exit type of this exit-action.
     */
    public int getExitType() {
        return this.exitType;
    }

    @Override
    public boolean executeAction(GameController g) {
        // TODO Change name to playernumber
        // TODO What to do when the server closes?
        return false;
    }

    @Override
    public String toString() {
        return "ExitAction{" +
                "name='" + name + '\'' +
                ", exitType=" + exitType +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExitAction that = (ExitAction) o;
        return getExitType() == that.getExitType() &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getExitType());
    }
}
