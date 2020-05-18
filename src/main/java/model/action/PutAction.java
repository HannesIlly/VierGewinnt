package model.action;

import java.util.Objects;

/**
 * The action, when a player puts a piece on the board (in a column).
 *
 * @author Hannes Illy
 */
public class PutAction extends Action {

    /**
     * The column, where the piece is put.
     */
    private final int column;
    /**
     * The piece, which is put in the column.
     */
    private final int piece;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PutAction putAction = (PutAction) o;
        return getColumn() == putAction.getColumn() &&
                getPiece() == putAction.getPiece();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColumn(), getPiece());
    }
}
