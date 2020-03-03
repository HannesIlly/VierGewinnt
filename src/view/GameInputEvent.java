package view;

import javafx.event.ActionEvent;
import javafx.event.EventType;

public class GameInputEvent extends ActionEvent {

    public static final EventType<GameInputEvent> PUT_PIECE = new EventType<GameInputEvent>("put piece");

    private int column = -1;

    public GameInputEvent(int column) {
        super.eventType = PUT_PIECE;

        this.column = column;
    }

    public int getColumn() {
        return this.column;
    }
}
