package util.action;

/**
 * The viergewinnt.util.action, when a player puts a piece on the board (in a column).
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
     * Creates a new put-viergewinnt.util.action with the given column and piece.
     *
     * @param column the column, where the piece is put.
     */
    public PutAction(int column, int piece) {
        super(ActionType.put);
        this.column = column;
        this.piece = piece;
    }

    @Override
    public byte[] encode() {
        byte[] data = new byte[2];
        data[0] = (byte) column;
        data[1] = (byte) piece;
        return data;
    }

    /**
     * Creates a put-viergewinnt.util.action from the given data.
     *
     * @param data The put viergewinnt.util.action data.
     * @return The created Action.
     * @throws IllegalArgumentException If the given data length is invalid.
     */
    public static PutAction decode(byte[] data) throws IllegalArgumentException {
        if (data.length != 2) {
            throw new IllegalArgumentException("Illegal length of data array. length = " + data.length);
        }
        return new PutAction(data[0], data[1]);
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

}
