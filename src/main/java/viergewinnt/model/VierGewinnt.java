package viergewinnt.model;

public class VierGewinnt {

    /**
     * The board on which the game is played.
     */
    private Board board;
    /**
     * The array index of the player. The piece number of the current player is always index+1.
     */
    private int currentPlayer = 0;
    /**
     * The player names.
     */
    private String[] players;
    /**
     * If the game has ended;
     */
    private boolean end = false;

    /**
     * The pieces that won the game. If the game has not yet ended, the array will be empty.
     */
    private int[][] winningPieces = new int[0][0];

    /**
     * Creates a new game of four-in-a-row with the given board and player names.
     *
     * @param board   the board on which is played
     * @param player1 the name of player1
     * @param player2 the name of player2
     */
    public VierGewinnt(Board board, String player1, String player2) {
        this.board = board;
        this.players = new String[2];
        this.players[0] = player1;
        this.players[1] = player2;
    }

    /**
     * Creates a new game of four-in-a-row with the default board and the given player names.
     *
     * @param player1 the name of player1
     * @param player2 the name of player2
     */
    public VierGewinnt(String player1, String player2) {
        this(new Board(), player1, player2);
    }

    /**
     * Creates a new game of four-in-a-row with the default board and player names "Player-1" and "Player-2".
     */
    public VierGewinnt() {
        this(new Board(), "Player-1", "Player-2");
    }

    /**
     * Places the current player's piece on the board and, if successful, changes the current player. The columns are
     * numbered from 1 to number of columns.
     *
     * @param column where the piece is placed (from 1 to number of columns)
     * @return if the operation was successful
     */
    public boolean placePiece(int column) {
        return placePiece(column, this.currentPlayer + 1);
    }

    /**
     * Attempts to place the given piece on the board and, if successful, changes the current player. The columns are
     * numbered from 1 to number of columns. If the given piece is not the current player's piece, the placement is
     * canceled.
     *
     * @param column where the piece is placed (from 1 to number of columns)
     * @param piece  The piece that is placed.
     * @return if the operation was successful
     */
    public boolean placePiece(int column, int piece) {
        if (this.end || this.currentPlayer + 1 != piece) {
            return false;
        }

        column--; // the number has to be decreased by 1 to represent the index correctly
        if (this.board.placePiece(column, this.currentPlayer + 1)) {
            if (this.isWinningPiece(column, this.board.getLastPiecePosition(column))) {
                this.end = true;
            } else {
                this.currentPlayer = 1 - this.currentPlayer;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns the current player.
     *
     * @return the name of the current player
     */
    public String getCurrentPlayer() {
        return this.players[currentPlayer];
    }

    /**
     * Returns the number of the current player.
     *
     * @return the current player's number
     */
    public int getCurrentPlayerNumber() {
        return this.currentPlayer;
    }

    /**
     * Returns if the game has ended.
     *
     * @return {@code true} if the game is over, {@code false} otherwise
     */
    public boolean hasEnded() {
        return this.end;
    }

    /**
     * Checks if the piece on the given field is part of a winning combination (four in a row).
     *
     * @param x the column of the piece
     * @param y the row of the piece
     * @return whether the piece is part of a winning combination
     */
    private boolean isWinningPiece(int x, int y) {
        // the pieces that (possibly) won the game
        int[][] pieces = new int[4][2];

        /*
         * 4 in a row when piece[x][y] == piece[x -1][y] 3 times in a row. So you have to count from e.g. 0 to 3 (three
         * increments), or 1 to 4
         */
        int count;

        // horizontal
        count = 0;
        for (int i = -2; i < 4; i++) {
            if (this.board.getField(x + i, y) == this.board.getField(x + i - 1, y)) {
                pieces[count][0] = x + i - 1;
                pieces[count][1] = y;
                count++;
            } else {
                count = 0;
            }
            if (count == 3) {
                // insert last (fourth) missing piece
                pieces[count][0] = x + i;
                pieces[count][1] = y;
                this.winningPieces = pieces;
                return true;
            }
        }
        // vertical
        count = 0;
        for (int i = -2; i < 4; i++) {
            if (this.board.getField(x, y + i) == this.board.getField(x, y + i - 1)) {
                pieces[count][0] = x;
                pieces[count][1] = y + i - 1;
                count++;
            } else {
                count = 0;
            }
            if (count == 3) {
                pieces[count][0] = x;
                pieces[count][1] = y + i;
                this.winningPieces = pieces;
                return true;
            }
        }
        // diagonal 1
        count = 0;
        for (int i = -2; i < 4; i++) {
            if (this.board.getField(x + i, y + i) == this.board.getField(x + i - 1, y + i - 1)) {
                pieces[count][0] = x + i - 1;
                pieces[count][1] = y + i - 1;
                count++;
            } else {
                count = 0;
            }
            if (count == 3) {
                pieces[count][0] = x + i;
                pieces[count][1] = y + i;
                this.winningPieces = pieces;
                return true;
            }
        }
        // diagonal 2
        count = 0;
        for (int i = -2; i < 4; i++) {
            if (this.board.getField(x - i, y + i) == this.board.getField(x - i + 1, y + i - 1)) {
                pieces[count][0] = x - i + 1;
                pieces[count][1] = y + i - 1;
                count++;
            } else {
                count = 0;
            }
            if (count == 3) {
                pieces[count][0] = x - i;
                pieces[count][1] = y + i;
                this.winningPieces = pieces;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a {@link BoardView} of the board of this game on the console.
     *
     * @return the {@link BoardView} of this board.
     */
    public BoardView createView() {
        return new BoardView(this.board);
    }

    /**
     * Returns the board on which the game is played.
     *
     * @return The game board.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Gets the names of all players.
     *
     * @return The array with all player names.
     */
    public String[] getPlayers() {
        return this.players;
    }

    /**
     * Gets an array with the coordinates of the winning pieces. If the game is not yet over, the array will be empty.
     * The coordinates are saved as follows: x-coordinate is array[number][0] and y-coordinate is array[number][1] (number from 0 to 3).
     *
     * @return The coordinates of the winning pieces in an array.
     */
    public int[][] getWinningPieces() {
        return this.winningPieces;
    }

    /**
     * Sets the name of both players.
     * @param firstPlayer The first player's name.
     * @param secondPlayer The second player's name.
     */
    public void setPlayerNames(String firstPlayer, String secondPlayer) {
        this.players[0] = firstPlayer;
        this.players[1] = secondPlayer;
    }

    /**
     * Sets the name of the given player. The playerNumber has to be 1 or 2.
     * @param playerNumber The player number whose name is set.
     * @param playerName The name of the player.
     */
    public void setPlayerName(int playerNumber, String playerName) {
        if (playerNumber == 1 || playerNumber == 2) {
            this.players[playerNumber - 1] = playerName;
        }
    }
}
