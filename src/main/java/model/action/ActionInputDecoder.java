package model.action;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A class that, when executed as a thread, reads the {@link InputStream} and decodes the given information into
 * actions.
 *
 * @author Hannes Illy
 */
public class ActionInputDecoder implements Runnable {
    /**
     * The input stream, from which the data is read.
     */
    private final DataInputStream in;
    /**
     * If the stream is already closed and cannot be read anymore.
     */
    private boolean isClosed = false;
    /**
     * The queue in which the decoded actions are stored. The access to it is possible via the {@code getAction()}
     * method. Both the method and the addition of actions to the queue are synchronized.
     */
    private final Queue<Action> actions;

    /**
     * Creates a new input decoder. This runnable has to be started in a thread, to begin reading and decoding the data
     * from the stream. If not started, the {@code getAction()} method will always return {@code null}.
     *
     * @param inputStream The input stream, from which is read.
     */
    public ActionInputDecoder(InputStream inputStream) {
        this.in = new DataInputStream(inputStream);
        this.actions = new LinkedList<Action>();
    }



    /**
     * Checks if the {@link InputStream} that is read is still active.
     *
     * @return If the {@link InputStream} is active.
     */
    public boolean isActive() {
        return !this.isClosed;
    }

    /**
     * Adds an action to the action queue (synchronized).
     *
     * @param newAction The action that is added to the action queue.
     */
    private void addAction(Action newAction) {
        synchronized (this.actions) {
            this.actions.add(newAction);
        }
    }

    /**
     * Gets the current action.
     *
     * @return The current action or {@code null} if there is none.
     */
    public Action getAction() {
        synchronized (this.actions) {
            return this.actions.poll();
        }
    }

    @Override
    public void run() {
        int currentType = Action.TYPE_UNDEFINED;

        while (!isClosed) {
            try {
                synchronized (in) {
                    try {
                        currentType = in.readByte();
                    } catch (EOFException e) {
                        // close this stream.
                        currentType = Action.TYPE_UNDEFINED;
                        System.out.println("Could not read action. End of File!");
                    }
                }
                // if end of stream
                if (currentType == Action.TYPE_UNDEFINED) {
                    this.close();
                    break;
                }

                switch (currentType) {
                    case Action.TYPE_NEW_PLAYER:
                        this.addAction(new NewPlayerAction(in.readUTF()));
                        break;
                    case Action.TYPE_PUT:
                        this.addAction(new PutAction(in.readInt(), in.readInt()));
                        break;
                    case Action.TYPE_NEW_GAME:
                        this.addAction(new NewGameAction());
                        break;
                    case Action.TYPE_EXIT:
                        String name = in.readUTF();
                        int exitType = in.readInt();

                        this.addAction(new ExitAction(name, exitType));
                        break;
                    case Action.TYPE_MESSAGE:
                        String source = in.readUTF();
                        String destination = in.readUTF();
                        String message = in.readUTF();

                        this.addAction(new MessageAction(source, destination, message));
                        break;
                    default:
                        System.out.println("Illegal action type. type = " + currentType);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the thread and the underlying stream.
     */
    public void close() {
        this.isClosed = true;
        synchronized (in) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
