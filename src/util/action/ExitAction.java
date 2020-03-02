package util.action;

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
    private String name;
    /**
     * If a player exits the game or the server closes..
     */
    private int exitType;
    
    /**
     * Creates a exit-action.
     * 
     * @param name
     *            The name of the player/server exiting the game.
     */
    public ExitAction(String name, int exitType) {
        super(ActionType.exit);
        this.name = name;
    }
    
    /**
     * Creates a exit-action, where a player exits the game.
     * 
     * @param name
     *            The name of the player exiting the game.
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
    public byte[] encode() {
        byte[] nameData = name.getBytes();
        byte[] data = new byte[nameData.length + 1];
        data[0] = (byte) this.exitType;
        for (int i = 0; i < nameData.length; i++) {
            data[i + 1] = nameData[i];
        }
        return data;
    }
    
    /**
     * Creates a exit-action from the given data.
     * 
     * @param data
     *            The action data.
     * @return The created action.
     */
    public static ExitAction decode(byte[] data) {
        String name;
        if (data.length > 0) {
            byte[] nameData = new byte[data.length - 1];
            for (int i = 0; i < nameData.length; i++) {
                nameData[i] = data[i + 1];
            }
            name = String.valueOf(data);
        } else {
            name = "";
        }
        return new ExitAction(name, data[0]);
    }
    
}
