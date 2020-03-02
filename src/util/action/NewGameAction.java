package util.action;

/**
 * Represents the action of starting a new game.
 * 
 * @author Hannes Illy
 */
public class NewGameAction extends Action {
    
    /**
     * Creates a new-game-action.
     */
    public NewGameAction() {
        super(ActionType.newGame);
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
    
    /**
     * Creates a new-game-action from the given data.
     * @param data The data for the action.
     * @return The created action.
     * @throws IllegalArgumentException If the given data length is invalid.
     */
    public static NewGameAction decode(byte[] data) throws IllegalArgumentException {
        if (data.length != 0) {
            throw new IllegalArgumentException("Illegal length of data array. length = " + data.length);
        }
        return new NewGameAction();
    }
}
