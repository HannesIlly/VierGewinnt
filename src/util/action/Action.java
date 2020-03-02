package util.action;

import java.io.IOException;
import java.io.OutputStream;

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
    protected ActionType type;
    
    /**
     * Creates a new action with the given type. Should be used by subclasses, to create a new action. The action data
     * has to be set in the subclass-constructor.
     * 
     * @param type
     *            The type of the action
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
     * Encodes this action and returns the encoded bytes.
     * 
     * @return The byte array containing the encoded action
     */
    public abstract byte[] encode();
    
    /**
     * Writes an action and its data to the given {@link OutputStream}.
     * 
     * @param type
     *            The action type
     * @param data
     *            The action data
     * @param out
     *            The {@link OutputStream}
     * @throws IOException
     *             If an exception occurs, when writing to the stream.
     */
    public static void write(ActionType type, byte[] data, OutputStream out) throws IOException {
        out.write(type.ordinal());
        out.write(data.length);
        out.write(data);
        out.write(0x00);
    }
    
    /**
     * Writes this action to the given {@link OutputStream}.
     * 
     * @param out
     *            The OutputStream to which is written
     * @throws IOException
     *             If an exception occurs, when writing to the stream
     */
    public void send(OutputStream out) throws IOException {
        write(this.type, this.encode(), out);
    }
}
