package util.action;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is used to encode given actions and immediately send them to the given {@link OutuputStream}.
 * 
 * @author Hannes Illy
 */
public class ActionOutputEncoder {
    /**
     * The stream to which the encoded data is written.
     */
    private OutputStream out;
    
    /**
     * Creates a new action encoder with the given {@link OuputStream}.
     * 
     * @param out
     *            The stream to which the data is written.
     */
    public ActionOutputEncoder(OutputStream out) {
        this.out = out;
    }
    
    /**
     * Sends the encoded action to the {@link OutputStream}.
     * 
     * @param action
     *            The action that is sent.
     */
    public void send(Action action) {
        if (out == null)
            return;
        byte[] data = action.encode();
        try {
            out.write(action.getType().ordinal());
            out.write(data.length);
            out.write(data);
            out.write(0);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Closes the underlying stream.
     */
    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
