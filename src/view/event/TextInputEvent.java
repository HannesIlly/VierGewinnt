package view.event;

import javafx.event.ActionEvent;

/**
 * This event is an action event that includes an input message to the event handler.
 *
 * @author Hannes Illy
 */
public class TextInputEvent extends ActionEvent {

    private String message;

    /**
     * Creates a new event with the associated text input.
     *
     * @param message The input message.
     */
    public TextInputEvent(String message) {
        super();

        this.message = message;
    }

    /**
     * Gets the text message associated with this event.
     * @return The input message.
     */
    public String getMessage() {
        return this.message;
    }
}
