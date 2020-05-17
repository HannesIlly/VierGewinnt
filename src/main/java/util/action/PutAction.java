package util.action;

/**
 * The action, when a player puts a piece on the board (in a column).
 *
 * @author Hannes Illy
 */
public class PutAction extends Action {

    /**
     * The column, where the piece is put.
     */
    private int column;
    /**
     * The piece, which is put in the column.
     */
    private int piece;

    /**
     * Creates a new put-action with the given column and piece.
     *
     * @param column the column, where the piece is put.
     */
    public PutAction(int column, int piece) {
        super(ActionType.put);
        this.column = column;
        this.piece = piece;
    }

    /**
     * Gets the column, where the piece is put in.
     *
     * @return The column number.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Gets the piece that is placed in the column.
     *
     * @return The piece number.
     */
    public int getPiece() {
        return this.piece;
    }

    @Override
    public String toString() {
        return "PutAction{" +
                "column=" + column +
                ", piece=" + piece +
                "} " + super.toString();
    }
}
