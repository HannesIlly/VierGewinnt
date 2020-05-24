package model.action;

import model.GameController;

/**
 * This class represents an action, which can be executed by the game. An action can be encoded in order to send it via
 * a network.
 *
 * @author Hannes Illy
 */
public abstract class Action {

    public static final int TYPE_UNDEFINED = 0;
    public static final int TYPE_EXIT = 1;
    public static final int TYPE_MESSAGE = 2;
    public static final int TYPE_NEW_GAME = 3;
    public static final int TYPE_NEW_PLAYER = 4;
    public static final int TYPE_PUT = 5;

    /**
     * The type of this action
     */
    protected final int type;

    /**
     * Creates a new action with the given type. Should be used by subclasses, to create a new action. The action data
     * has to be set in the subclass-constructor.
     *
     * @param type The type of the action
     */
    protected Action(int type) {
        this.type = type;
    }

    /**
     * Gets the type of this action.
     *
     * @return The {@link int}.
     */
    public int getType() {
        return this.type;
    }

    public abstract boolean executeAction(GameController g);

    /**
     * Gets the action type as a string. Other actions might want to override this method to additionally return action data.
     *
     * @return The action in its string representation.
     */
    public String toString() {
        String s = null;
        switch (this.type) {
            case TYPE_EXIT:
                s = "exit action";
                break;
            case TYPE_MESSAGE:
                s = "message action";
                break;
            case TYPE_NEW_GAME:
                s = "new game action";
                break;
            case TYPE_NEW_PLAYER:
                s = "new player action";
                break;
            case TYPE_PUT:
                s = "put action";
                break;
        }
        return s;
    }

}
