package model.action;

import model.GameController;

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
        super(Action.TYPE_NEW_GAME);
    }



    @Override
    public boolean executeAction(GameController g) {
        return g.startGame();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass();
    }
}
