package model.action;

/**
 * This class represents an action, which can be executed by the game. An action can be encoded in order to send it via
 * a network.
 *
 * @author Hannes Illy
 */
public abstract class Action {

    /**
     * The type of this action
     */
    protected final ActionType type;

    /**
     * Creates a new action with the given type. Should be used by subclasses, to create a new action. The action data
     * has to be set in the subclass-constructor.
     *
     * @param type The type of the action
     */
    protected Action(ActionType type) {
        this.type = type;
    }

    /**
     * Gets the type of this action.
     *
     * @return The {@link ActionType}.
     */
    public ActionType getType() {
        return this.type;
    }

    /**
     * Gets the action type as a string. Other actions might want to override this method to additionally return action data.
     *
     * @return The action in its string representation.
     */
    public String toString() {
        return this.type.toString();
    }

}
