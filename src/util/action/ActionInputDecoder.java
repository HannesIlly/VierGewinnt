package util.action;

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
    private InputStream in;
    /**
     * If the stream is already closed and cannot be read anymore.
     */
    private boolean isClosed = false;
    /**
     * The queue in which the decoded actions are stored. The access to it is possible via the {@code getAction()}
     * method. Both the method and the adding of actions to the queue are synchronized.
     */
    private Queue<Action> actions;

    /**
     * Creates a new input decoder. This runnable has to be started in a thread, to begin reading and decoding the data
     * from the stream. If not started, the {@code getAction()} method will always return {@code null}.
     *
     * @param inputStream The input stream, from which is read.
     */
    public ActionInputDecoder(InputStream inputStream) {
        this.in = inputStream;
        this.actions = new LinkedList<Action>();
    }

    /**
     * Creates a new action from the given data. The decoding-overhead-data should be included in the argument array.
     *
     * @param data The read action data (including the overhead).
     * @return The created action.
     */
    private Action createAction(byte[] data) {
        if (data.length < 3)
            throw new IllegalArgumentException("The length of the given data is invalid. length = " + data.length);
        ActionType[] allTypes = ActionType.values();
        if (data[0] >= allTypes.length)
            throw new IllegalArgumentException("The action given in the data is unknown. action = " + data[0]);
        ActionType type = allTypes[data[0]];
        byte[] actionData = new byte[data.length - 3];
        for (int i = 0; i < actionData.length; i++) {
            actionData[i] = data[i + 2];
        }
        switch (type) {
            case newPlayer:
                return NewPlayerAction.decode(actionData);
            case put:
                return PutAction.decode(actionData);
            case newGame:
                return NewGameAction.decode(actionData);
            case exit:
                return ExitAction.decode(actionData);
            case undo:
                // TODO undo action
            case message:
                // TODO message action
            case error:
                // TODO error action
            default:
                throw new IllegalArgumentException("Illegal action type. type = " + type);
        }
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
        int input = 0;
        byte readByte = 0;
        byte firstByte = 0;
        boolean newDataStart = true;
        byte[] readData = {};
        int currentDataPosition = 0;

        while (!isClosed) {
            try {
                // read byte and check if stream is still open
                synchronized (in) {
                    input = in.read();
                    if (input == -1) {
                        in.close();
                        break; // exit the thread
                    }
                }

                readByte = (byte) input;

                if (newDataStart) {
                    // Signal that the next incoming data belongs to this one
                    newDataStart = false;
                    // save first read byte for later
                    firstByte = readByte;
                    // Set the current position to 0. The position is increased at the end of the loop.
                    currentDataPosition = 0;
                } else {
                    if (currentDataPosition == 1) {
                        // The second read byte is the data length (the overhead of three bytes has to be added)
                        readData = new byte[readByte + 3];
                        // fill the first (saved) bytes
                        readData[0] = firstByte;
                        readData[1] = readByte;
                    } else if (currentDataPosition < readData.length - 1) { // everything except the last byte
                        // save readByte. The data position is increased at the end.
                        readData[currentDataPosition] = readByte;
                    } else { // the (last) control-byte
                        if (readByte == 0) {
                            readData[currentDataPosition] = readByte;
                            // add the action to the list (thread-safe!)
                            synchronized (this.actions) {
                                this.actions.add(this.createAction(readData));
                            }
                        }
                        newDataStart = true;
                    }

                }
                currentDataPosition++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.isClosed = true;
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
