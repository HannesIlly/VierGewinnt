package util.action;

/**
 * Represents the action of a new player joining the game.
 * 
 * @author Hannes Illy
 */
public class NewPlayerAction extends Action {
    
    /**
     * The name of the player.
     */
    private String name;
    
    /**
     * Creates a new-player-action with the given player name.
     * 
     * @param name The name of the new player.
     */
    public NewPlayerAction(String name) {
        super(ActionType.newPlayer);
        this.name = name;
    }
    
    /**
     * Gets the name of the player joining the game.
     * @return The name of the player.
     */
    public String getName() {
        return this.name;
    }
    
    @Override
    public byte[] encode() {
        return name.getBytes();
    }
    
    /**
     * Creates a new-player-action from the given data.
     * 
     * @param data The action data.
     * @return The created action.
     */
    public static NewPlayerAction decode(byte[] data) {
        return new NewPlayerAction(String.valueOf(data));
    }
    
}
