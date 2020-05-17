package model;

/**
 * A game of Four-in-a-row.
 *
 * @author Hannes Illy
 */
public class VierGewinnt {

    /**
     * The board on which the game is played.
     */
    private Board board;
    /**
     * Must not be 0. The piece number on the board of the current player is always currentPlayer.
     */
    private int currentPlayer = 1;
    /**
     * If the game has ended;
     */
    private boolean end = false;
    /**
     * The number of the winning player (-1 = not finished, 0 = draw).
     */
    private int winningPlayer = -1;
    /**
     * The pieces that won the game. If the game has not yet ended, the array will be empty.
     */
    private int[][] winningPieces = new int[0][0];

    /**
     * Creates a new game of four-in-a-row with the default size.
     */
    public VierGewinnt() {
        this.board = new Board();
    }

    /**
     * Creates a new game of four-in-a-row with the specified size.
     *
     * @param columns The number of columns on the board.
     * @param rows    The number of rows on the board.
     */
    public VierGewinnt(int columns, int rows) {
        this.board = new Board(rows, columns);
    }


    /**
     * Returns the number of columns in this game.
     *
     * @return The number of columns on the board.
     */
    public int getColumns() {
        return this.board.getColumns();
    }

    /**
     * Returns the number of rows in this game.
     *
     * @return The number of rows on the board.
     */
    public int getRows() {
        return this.board.getRows();
    }

    /**
     * Gets the content of a single fied. 0 - empty, 1 - player1, 2 - player2, -1 - illegal coordinates
     *
     * @param column The number of the column. Legal values are from 0 to getColumns() - 1.
     * @param row    The number of the row. Legal values are from 0 to getRows() - 1.
     * @return The content of the field or {@code -1} if the coordinates were illegal.
     */
    public int getField(int column, int row) {
        return this.board.getField(column, row);
    }

    /**
     * Returns the number of the current player. The piece number that this player places is returned.
     *
     * @return The current player's number.
     */
    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Switches the current player. The values are either 1 or 2.
     */
    private void switchCurrentPlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
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
     * Gets the number of the player that won the game. It returns -1 if the game has not yet ended and 0 if the game is a draw.
     *
     * @return The player that won the game or {@code -1} (not finished) or {@code 0} (draw).
     */
    public int getWinningPlayer() {
        return this.winningPlayer;
    }

    /**
     * Places the current player's piece on the board and, if successful, changes the current player. The columns are
     * numbered from 1 to number of columns.
     *
     * @param column where the piece is placed (from 1 to number of columns)
     * @return if the operation was successful
     */
    public boolean placePiece(int column) {
        return placePiece(column, this.currentPlayer);
    }

    /**
     * Attempts to place the given piece on the board and, if successful, changes the current player. The columns are
     * numbered from 0 to getColumns() - 1. If the given piece is not the current player's piece, the placement is
     * canceled.
     *
     * @param column where the piece is placed (from 0 to getColumns() - 1)
     * @param piece  The piece that is placed.
     * @return if the operation was successful
     */
    public boolean placePiece(int column, int piece) {
        // check for illegal piece number
        if (this.end || this.currentPlayer != piece) {
            return false;
        }

        if (this.board.placePiece(column, this.currentPlayer)) {
            if (this.isWinningPiece(column, this.board.getLastPiecePosition(column))) {
                this.end = true;
                this.winningPlayer = getCurrentPlayer(); // current player won
            } else if (this.board.isFull()) {
                this.end = true;
                this.winningPlayer = 0; // draw
            } else {
                switchCurrentPlayer();
            }
            return true;
        }
        return false;
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
     * Gets an array with the coordinates of the winning pieces. If the game is not yet over, the array will be empty.
     * The coordinates are saved as follows: x-coordinate is array[number][0] and y-coordinate is array[number][1] (number from 0 to 3).
     *
     * @return The coordinates of the winning pieces in an array.
     */
    public int[][] getWinningPieces() {
        return this.winningPieces;
    }

}
