package util.action;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class represents an viergewinnt.util.action, which can be executed by the game. An viergewinnt.util.action can be encoded in order to send it via
 * a network.
 *
 * @author Hannes Illy
 */
public abstract class Action {

    /**
     * The type of this viergewinnt.util.action
     */
    protected ActionType type;

    /**
     * Creates a new viergewinnt.util.action with the given type. Should be used by subclasses, to create a new viergewinnt.util.action. The viergewinnt.util.action data
     * has to be set in the subclass-constructor.
     *
     * @param type The type of the viergewinnt.util.action
     */
    protected Action(ActionType type) {
        this.type = type;
    }

    /**
     * Gets the type of this viergewinnt.util.action.
     *
     * @return The {@link ActionType}.
     */
    public ActionType getType() {
        return this.type;
    }

    /**
     * Encodes this viergewinnt.util.action and returns the encoded bytes.
     *
     * @return The byte array containing the encoded viergewinnt.util.action
     */
    public abstract byte[] encode();

    /**
     * Writes an viergewinnt.util.action and its data to the given {@link OutputStream}.
     *
     * @param type The viergewinnt.util.action type
     * @param data The viergewinnt.util.action data
     * @param out  The {@link OutputStream}
     * @throws IOException If an exception occurs, when writing to the stream.
     */
    public static void write(ActionType type, byte[] data, OutputStream out) throws IOException {
        out.write(type.ordinal());
        out.write(data.length);
        out.write(data);
        out.write(0x00);
        out.flush();
    }

    /**
     * Writes this viergewinnt.util.action to the given {@link OutputStream}.
     *
     * @param out The OutputStream to which is written
     * @throws IOException If an exception occurs, when writing to the stream
     */
    public void send(OutputStream out) throws IOException {
        write(this.type, this.encode(), out);
    }

    /**
     * Gets the viergewinnt.util.action type as a string. Other actions might want to override this method to additionally return viergewinnt.util.action data.
     *
     * @return The viergewinnt.util.action in its string representation.
     */
    public String toString() {
        return this.type.toString();
    }

}
