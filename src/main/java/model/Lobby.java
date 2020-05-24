package model;

public class Lobby {

    private Player[] players;

    private int startingPlayer = 0;

    public Lobby() {
        int maxPlayerCount = 2;
        this.players = new Player[maxPlayerCount];
    }

    /**
     * Adds the given player to the player list. Returns the player number.
     *
     * @param player The player that is added.
     * @return The number that was assigned to this player. If the player could not be added {@code -1} is returned.
     */
    public int join(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes a player from the lobby.
     *
     * @param playerNumber The player number.
     */
    public void leave(int playerNumber) {
        if (0 < playerNumber && playerNumber < this.players.length) {
            players[playerNumber] = null;
        }
    }

    /**
     * Gets the player with the given number.
     *
     * @param playerNumber The player number.
     * @return The player with the given number.
     */
    public Player getPlayer(int playerNumber) {
        return players[playerNumber];
    }

    /**
     * Checks if every player in the lobby is ready, so the game can be started.
     *
     * @return Returns {@code true} if every player is ready, {@code false} otherwise.
     */
    public boolean isReady() {
        for (Player p : this.players) {
            if (p == null || p.isReady()) {
                return false;
            }
        }
        return true;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
