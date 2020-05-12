package model;

public class Board {
    /**
     * the board where the pieces are placed: 0 = empty, 1 = player1, 2 = player2
     */
    private int[][] board;
    
    /**
     * Creates a new board for the game "Four-in-a-row". There are two different pieces/colours and pieces can only be
     * placed in columns, where they are automatically put on top of the other pieces there.
     * 
     * @param rows
     *            the number of rows
     * @param columns
     *            the number of columns
     */
    public Board(int rows, int columns) {
        this.board = new int[columns][rows];
    }
    
    /**
     * Creates a new board for the game "Four-in-a-row" in default size (7 cols, 6 rows). There are two different
     * pieces/colours and pieces can only be placed in columns, where they are automatically put on top of the other
     * pieces there.
     */
    public Board() {
        this(6, 7);
    }
    
    /**
     * Returns the board's number of rows.
     * 
     * @return the number of rows
     */
    public int getRows() {
        return this.board[0].length;
    }
    
    /**
     * Returns the board's number of columns.
     * 
     * @return the number of columns
     */
    public int getColumns() {
        return this.board.length;
    }
    
    /**
     * Chec
     *
     *
     *
     *
     *
     * ks whether the given coordinates are inside the bounds of the board, or if they are invalid.
     * 
     * @param column
     *            the coordinate for the column
     * @param row
     *            the coordinate for the row
     * @return if the coordinates are valid
     */
    private boolean areValidCoordinates(int column, int row) {
        if (column >= 0 && column < this.getColumns() && row >= 0 && row < this.getRows()) {
            return true;
        }
        return false;
    }
    
    /**
     * Places a piece on the board. The piece can either be 1 or 2. If this operation is successful it returns {@code true},
     * otherwise {@code false}.
     * 
     * @param column
     *            the column, in which the piece is placed
     * @param piece
     *            the piece that is placed
     * @return if the piece could be placed
     */
    public boolean placePiece(int column, int piece) {
        // only allow valid pieces (1 and 2).
        if (piece != 1 && piece != 2) {
            return false;
        }
        if (column >= 0 && column < this.getColumns()) {
            // Iterate through column and place piece at the end (if possible).
            for (int i = 0; i < this.board[column].length; i++) {
                if (this.board[column][i] == 0) {
                    this.board[column][i] = piece;
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if the board is full.
     * @return {@code true} if the board is full, {@code false} otherwise.
     */
    public boolean isFull() {
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i][this.board[i].length - 1] == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns the piece at the given coordinates. If the coordinates are invalid {@code -1} is returned.
     * @param column the column of the piece
     * @param row the row of the piece
     * @return the piece or {@code -1} if the coordinates are invalid
     */
    public int getField(int column, int row) {
        if (this.areValidCoordinates(column, row)) {
            return this.board[column][row];
        }
        return -1;
    }
    
    /**
     * Returns the row of the last piece in this column.
     * 
     * @param column the column of the piece
     * @return the row of the last piece or {@code -1} if there is no piece or the column is invalid
     */
    public int getLastPiecePosition(int column) {
        int lastPosition = -1;
        if (column >= 0 && column < this.getColumns()) {
            for (int i = 0; i < this.board[column].length; i++) {
                if (this.board[column][i] != 0) {
                    lastPosition = i;
                } else {
                    return lastPosition;
                }
            }
            return lastPosition;
        }
        return -1;
    }
}
