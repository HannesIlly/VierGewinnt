package util.action;

/**
 * Represents the viergewinnt.util.action of a new player joining the game.
 *
 * @author Hannes Illy
 */
public class NewPlayerAction extends Action {

    /**
     * The name of the player.
     */
    private String name;

    /**
     * Creates a new-player-viergewinnt.util.action with the given player name.
     *
     * @param name The name of the new player.
     */
    public NewPlayerAction(String name) {
        super(ActionType.newPlayer);
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
    public byte[] encode() {
        byte[] encodedData = new byte[name.length() * 2];
        char[] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            encodedData[i] = (byte) chars[i];
            encodedData[i + 1] = (byte) (chars[i] >> 8);
        }
        return encodedData;
    }

    /**
     * Creates a new-player-viergewinnt.util.action from the given data.
     *
     * @param data The viergewinnt.util.action data.
     * @return The created viergewinnt.util.action.
     */
    public static NewPlayerAction decode(byte[] data) {
        char[] decodedData = new char[data.length / 2];
        for (int i = 0; i < decodedData.length; i++) {
            decodedData[i] = (char) (data[2 * i] | (data[2 * i + 1] << 8));
        }
        return new NewPlayerAction(new String(decodedData));
    }

    @Override
    public String toString() {
        return super.toString() + ", name=" + this.name;
    }

}
