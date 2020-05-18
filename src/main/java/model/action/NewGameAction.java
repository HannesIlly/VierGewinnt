package model.action;

/**
 * Represents the action of starting a new game.
 *
 * @author Hannes Illy
 */
public class NewGameAction extends Action {

    /**
     * Creates a new-game-action.
     */
    public NewGameAction() {
        super(ActionType.newGame);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass();
    }
}
