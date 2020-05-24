package model.action;

import model.GameController;

import java.util.Objects;

/**
 * Represents the action of one entity (player/server) sending a message to someone else.
 *
 * @author Hannes Illy
 */
public class MessageAction extends Action {

    private final String source;
    private final String destination;
    private final String message;

    /**
     * Creates a new message action with the given source, destination and message.
     */
    protected MessageAction(String source, String destination, String message) {
        super(Action.TYPE_MESSAGE);

        this.source = source;
        this.destination = destination;
        this.message = message;
    }

    /**
     * Gets the name of this message's source.
     *
     * @return The source of the message.
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets the name of this message's destination.
     *
     * @return The destination of this message.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Gets the message.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean executeAction(GameController g) {
        // TODO How are messages stored? --> probably in the GameController
        return false;
    }

    @Override
    public String toString() {
        return "MessageAction{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageAction that = (MessageAction) o;
        return Objects.equals(getSource(), that.getSource()) &&
                Objects.equals(getDestination(), that.getDestination()) &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getDestination(), getMessage());
    }
}
