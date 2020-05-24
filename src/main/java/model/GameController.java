package model;

public class GameController {

    Lobby lobby;
    VierGewinnt game;

    boolean isInLobby = true;

    public GameController() {
        lobby = new Lobby();
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public VierGewinnt getGame() {
        return this.game;
    }

    public boolean startGame() {
        if (lobby.isReady()) {
            this.isInLobby = false;
            //TODO create instance of VierGewinnt
            return true;
        }
        return false;
    }

    public boolean hasStarted() {
        return !isInLobby;
    }

}
