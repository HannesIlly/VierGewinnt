package util.action;

/**
 * Represents the action of one entity (player/server) sending a message to someone else.
 *
 * @author Hannes Illy
 */
public class MessageAction extends Action {

    private String source;
    private String destination;
    private String message;

    /**
     * Creates a new message action with the given source, destination and message.
     */
    protected MessageAction(String source, String destination, String message) {
        super(ActionType.message);

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
    public String toString() {
        return "MessageAction{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
