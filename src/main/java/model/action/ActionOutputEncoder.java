package model.action;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is used to encode given actions and immediately send them to the given {@link OutputStream}.
 *
 * @author Hannes Illy
 */
public class ActionOutputEncoder {
    /**
     * The stream to which the encoded data is written.
     */
    private DataOutputStream out;

    /**
     * Creates a new action encoder with the given {@link OutputStream}.
     *
     * @param out The stream to which the data is written.
     */
    public ActionOutputEncoder(OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    /**
     * Sends the encoded action to the {@link OutputStream}.
     *
     * @param action The action that is sent.
     */
    public void send(Action action) {
        if (out == null)
            return;

        try {
            switch (action.getType()) {
                case newPlayer:
                    out.writeUTF(((NewPlayerAction) action).getName());
                    break;
                case put:
                    out.writeInt(((PutAction) action).getColumn());
                    out.writeInt(((PutAction) action).getPiece());
                    break;
                case newGame:
                    break;
                case exit:
                    out.writeUTF(((ExitAction) action).getName());
                    out.writeInt(((ExitAction) action).getExitType());
                    break;
                case message:
                    out.writeUTF(((MessageAction) action).getSource());
                    out.writeUTF(((MessageAction) action).getDestination());
                    out.writeUTF(((MessageAction) action).getMessage());
                default:
                    throw new IllegalArgumentException("Illegal action type. type = " + action.getType());
            }
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
            out = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
