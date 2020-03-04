package model;

import java.util.Map;
import java.util.TreeMap;

/**
 * This class is a console view of the class {@link Board}.
 */
public class BoardView {

    /**
     * The board, that this view is based on.
     */
    private Board board;

    /**
     * The appearance of the numbers of the board - as characters in the output.
     */
    private Map<Integer, Character> appearance = new TreeMap<Integer, Character>();

    /**
     * Creates a new console-view of the board.
     */
    public BoardView(Board board) {
        this.board = board;
        this.setStandardAppearance();
    }

    /**
     * Sets the character which is displayed for every field, that contains the given number.
     *
     * @param number the number, which is on the field
     * @param symbol the symbol, that represents the number
     */
    public void setAppearanceChar(int number, char symbol) {
        this.appearance.put(number, symbol);
    }

    /**
     * Sets the appearance of the fields to the standard characters: 0: {@code' '}, 1: {@code'X'}, 2: {@code'O'}.
     */
    public void setStandardAppearance() {
        this.appearance.clear();
        this.appearance.put(0, ' ');
        this.appearance.put(1, 'X');
        this.appearance.put(2, 'O');
    }

    /**
     * Prints the whole board on the console. The appearance of the field numbers can be changed with
     * {@code setStandardAppearance()} or {@code setAppearanceChar(int, char)}.
     */
    public void printBoard() {
        int currentRow = this.board.getRows() - 1;
        this.printColumnNumbers();
        this.printRowSeparator();
        while (currentRow >= 0) {
            this.printRow(currentRow);
            this.printRowSeparator();
            currentRow--;
        }
    }

    /**
     * Prints a row with the content of the fields.
     *
     * @param row the row that is printed
     */
    private void printRow(int row) {
        String line = "|";
        Character symbol;
        for (int i = 0; i < this.board.getColumns(); i++) {
            symbol = this.appearance.get(this.board.getField(i, row));
            if (symbol == null) {
                symbol = Character.forDigit(this.board.getField(i, row), 10);
            }
            line += " " + symbol + " |";
        }
        System.out.println(line);
    }

    /**
     * Prints a separator-row between two content-rows.
     */
    private void printRowSeparator() {
        String line = "+";
        for (int i = 0; i < this.board.getColumns(); i++) {
            line += "---+";
        }
        System.out.println(line);
    }

    /**
     * Prints the numbers from 1 to the number of columns of the board.
     */
    private void printColumnNumbers() {
        String line = "  ";
        for (int i = 0; i < this.board.getColumns(); i++) {
            line += Character.forDigit(i + 1, 10) + "   ";
        }
        System.out.println(line);
    }

}
