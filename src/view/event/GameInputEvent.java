package view.event;

import javafx.event.ActionEvent;
import javafx.event.EventType;

public class GameInputEvent extends ActionEvent {

    public static final EventType<GameInputEvent> PUT_PIECE = new EventType<GameInputEvent>("put piece");
    public static final EventType<GameInputEvent> PLAYER_JOIN = new EventType<GameInputEvent>("player joining the game");


    private String playerName = "";
    private int column = -1;

    public GameInputEvent(int column) {
        super.eventType = PUT_PIECE;

        this.column = column;
    }

    public GameInputEvent(String name) {
        if (name == null) {
            throw new NullPointerException("Der eingegebene Name darf nicht null sein!");
        }

        super.eventType = PLAYER_JOIN;

        this.playerName = name;
    }

    public int getColumn() {
        return this.column;
    }

    public String getPlayerName() {
        return this.playerName;
    }
}
