package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    private static final int DEFAULT_COLUMNS = 7;
    private static final int DEFAULT_ROWS = 6;

    /**
     * Tests the default constructor and getRows, getColumns.
     */
    @Test
    public void BoardTest1() {
        board = new Board();

        assertEquals(DEFAULT_ROWS, board.getRows(), "The height of the standard board does not match.");
        assertEquals(DEFAULT_COLUMNS, board.getColumns(), "The width of the standard board does not match.");
    }

    /**
     * Tests the constructor with parameters and getRows, getColumns.
     */
    @Test
    public void BoardTest2() {
        int rows = 3;
        int columns = 2;
        board = new Board(rows, columns);

        assertEquals(rows, board.getRows(), "The height of the board does not match.");
        assertEquals(columns, board.getColumns(), "The width of the board does not match.");
    }

    /**
     * Tests the constructor with parameters and getRows, getColumns.
     */
    @Test
    public void BoardTest3() {
        int rows = 15;
        int columns = 15;
        board = new Board(rows, columns);

        assertEquals(rows, board.getRows(), "The height of the board does not match.");
        assertEquals(columns, board.getColumns(), "The width of the board does not match.");
    }

    /**
     * Tests the constructor with parameters and getRows, getColumns.
     */
    @Test
    public void BoardTest4() {
        int rows = 8;
        int columns = 1;
        board = new Board(rows, columns);

        assertEquals(rows, board.getRows(), "The height of the board does not match.");
        assertEquals(columns, board.getColumns(), "The width of the board does not match.");
    }

    /**
     * Tests the method getField with valid and invalid coordinates.
     * In this test the board has default size and is empty (Tests with pieces are made at placePieceTest).
     */
    @Test
    public void getFieldTest1() {
        board = new Board();

        // valid coordinates (empty)
        assertEquals(0, board.getField(0, 0), "The returned value of the field is incorrect.");
        assertEquals(0, board.getField(DEFAULT_COLUMNS - 1, DEFAULT_ROWS - 1), "The returned value of the field is incorrect.");
        assertEquals(0, board.getField(4, 2), "The returned value of the field is incorrect.");

        // invalid coordinates
        assertEquals(-1, board.getField(-1, 2), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(0, -5), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(-10, -34), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(DEFAULT_COLUMNS, DEFAULT_ROWS), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(DEFAULT_COLUMNS + 2, DEFAULT_ROWS), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(DEFAULT_COLUMNS + 12, DEFAULT_ROWS + 51), "The returned value for an illegal field is incorrect.");
    }

    /**
     * Tests the method getField with valid and invalid coordinates.
     * In this test the board has a custom size and is empty (Tests with pieces are made at placePieceTest).
     */
    @Test
    public void getFieldTest2() {
        int rows = 9;
        int columns = 34;
        board = new Board(rows, columns);

        // valid coordinates (empty)
        assertEquals(0, board.getField(0, 0), "The returned value of the field is incorrect.");
        assertEquals(0, board.getField(columns - 1, rows - 1), "The returned value of the field is incorrect.");
        assertEquals(0, board.getField(4, 2), "The returned value of the field is incorrect.");
        assertEquals(0, board.getField(23, 5), "The returned value of the field is incorrect.");

        // invalid coordinates
        assertEquals(-1, board.getField(-1, 2), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(0, -5), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(-10, -34), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(columns, rows), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(columns + 2, rows), "The returned value for an illegal field is incorrect.");
        assertEquals(-1, board.getField(columns + 12, rows + 51), "The returned value for an illegal field is incorrect.");
    }

    /**
     * Place different pieces on a default board. The pieces are placed in valid columns.
     */
    @Test
    public void placePieceTest1() {
        board = new Board();

        // piece 1
        assertTrue(board.placePiece(0, 1));
        assertEquals(1, board.getField(0, 0));

        assertTrue(board.placePiece(0, 1));
        assertEquals(1, board.getField(0, 1));

        assertTrue(board.placePiece(DEFAULT_COLUMNS - 1, 1));
        assertEquals(1, board.getField(DEFAULT_COLUMNS - 1, 0));

        // piece 2
        assertTrue(board.placePiece(5, 2));
        assertEquals(2, board.getField(5, 0));

        assertTrue(board.placePiece(0, 2));
        assertEquals(2, board.getField(0, 2));

        assertTrue(board.placePiece(2, 2));
        assertEquals(2, board.getField(2, 0));
    }

    /**
     * Place different pieces on a default board. Valid pieces are placed at invalid locations.
     */
    @Test
    public void placePieceTest2() {
        board = new Board();

        // invalid locations
        assertFalse(board.placePiece(-1, 1));
        assertFalse(board.placePiece(DEFAULT_COLUMNS, 1));
        assertFalse(board.placePiece(DEFAULT_COLUMNS + 32, 1));
    }

    /**
     * Place different pieces on a default board. Invalid pieces are placed on the board.
     */
    @Test
    public void placePieceTest3() {
        board = new Board();

        // invalid pieces
        assertFalse(board.placePiece(0, 0));
        assertFalse(board.placePiece(DEFAULT_COLUMNS - 1, 0));
        assertFalse(board.placePiece(3, 0));

        assertFalse(board.placePiece(0, 3));
        assertEquals(0, board.getField(0, 0));
        assertFalse(board.placePiece(DEFAULT_COLUMNS - 1, 3));
        assertEquals(0, board.getField(DEFAULT_COLUMNS - 1, 0));
        assertFalse(board.placePiece(5, 3));
        assertEquals(0, board.getField(5, 0));

        assertFalse(board.placePiece(0, -1));
        assertEquals(0, board.getField(0, 0));
        assertFalse(board.placePiece(DEFAULT_COLUMNS - 1, -1));
        assertEquals(0, board.getField(DEFAULT_COLUMNS - 1, 0));
        assertFalse(board.placePiece(3, -1));
        assertEquals(0, board.getField(3, 0));
    }

    /**
     * Place different pieces on a custom size board. The pieces are placed in valid columns.
     */
    @Test
    public void placePieceTest4() {
        int rows = 5;
        int columns = 4;
        board = new Board(rows, columns);

        // piece 1
        assertTrue(board.placePiece(0, 1));
        assertEquals(1, board.getField(0, 0));

        assertTrue(board.placePiece(0, 1));
        assertEquals(1, board.getField(0, 1));

        assertTrue(board.placePiece(columns - 1, 1));
        assertEquals(1, board.getField(columns - 1, 0));

        // piece 2
        assertTrue(board.placePiece(1, 2));
        assertEquals(2, board.getField(1, 0));

        assertTrue(board.placePiece(0, 2));
        assertEquals(2, board.getField(0, 2));

        assertTrue(board.placePiece(2, 2));
        assertEquals(2, board.getField(2, 0));
    }

    /**
     * Place different pieces on a custom size board. Valid pieces are placed at invalid locations.
     */
    @Test
    public void placePieceTest5() {
        int rows = 9;
        int columns = 6;
        board = new Board(rows, columns);

        // invalid locations
        assertFalse(board.placePiece(-1, 1));
        assertFalse(board.placePiece(columns, 1));
        assertFalse(board.placePiece(columns + 32, 1));
    }

    /**
     * Place different pieces on a custom size board. Invalid pieces are placed on the board.
     */
    @Test
    public void placePieceTest6() {
        int rows = 8;
        int columns = 7;
        board = new Board(rows, columns);

        // invalid pieces
        assertFalse(board.placePiece(0, 0));
        assertFalse(board.placePiece(columns - 1, 0));
        assertFalse(board.placePiece(3, 0));

        assertFalse(board.placePiece(0, 3));
        assertEquals(0, board.getField(0, 0));
        assertFalse(board.placePiece(columns - 1, 3));
        assertEquals(0, board.getField(columns - 1, 0));
        assertFalse(board.placePiece(5, 3));
        assertEquals(0, board.getField(5, 0));

        assertFalse(board.placePiece(0, -1));
        assertEquals(0, board.getField(0, 0));
        assertFalse(board.placePiece(columns - 1, -1));
        assertEquals(0, board.getField(columns - 1, 0));
        assertFalse(board.placePiece(3, -1));
        assertEquals(0, board.getField(3, 0));
    }

    /**
     * Place too many pieces in a column of a default board.
     */
    @Test
    public void placePieceTest7() {
        board = new Board();

        for (int i = 0; i < DEFAULT_ROWS; i++) {
            assertTrue(board.placePiece(0, 1));
        }
        assertFalse(board.placePiece(0, 1));

        for (int i = 0; i < DEFAULT_ROWS; i++) {
            assertTrue(board.placePiece(3, 1));
        }
        assertFalse(board.placePiece(3, 1));
    }

    /**
     * Place too many pieces in a column of a custom size board.
     */
    @Test
    public void placePieceTest8() {
        int rows = 13;
        int columns = 4;
        board = new Board(rows, columns);

        for (int i = 0; i < rows; i++) {
            assertTrue(board.placePiece(0, 1));
        }
        assertFalse(board.placePiece(0, 1));

        for (int i = 0; i < rows; i++) {
            assertTrue(board.placePiece(1, 1));
        }
        assertFalse(board.placePiece(1, 1));
    }

    /**
     * Get last piece position of empty or invalid columns on a default board.
     */
    @Test
    public void getLastPiecePositionTest1() {
        board = new Board();

        assertEquals(-1, board.getLastPiecePosition(-1));
        assertEquals(-1, board.getLastPiecePosition(0));
        assertEquals(-1, board.getLastPiecePosition(4));
        assertEquals(-1, board.getLastPiecePosition(DEFAULT_COLUMNS - 1));
        assertEquals(-1, board.getLastPiecePosition(DEFAULT_COLUMNS));
    }

    /**
     * Get last piece position of valid columns on a default board.
     */
    @Test
    public void getLastPiecePositionTest2() {
        board = new Board();

        assertTrue(board.placePiece(0, 1));
        assertEquals(0, board.getLastPiecePosition(0));

        assertTrue(board.placePiece(DEFAULT_COLUMNS - 1, 2));
        assertEquals(0, board.getLastPiecePosition(DEFAULT_COLUMNS - 1));

        assertTrue(board.placePiece(0, 1));
        assertEquals(1, board.getLastPiecePosition(0));
    }

    /**
     * Get last piece position of empty or invalid columns on a custom size board.
     */
    @Test
    public void getLastPiecePositionTest3() {
        int rows = 8;
        int columns = 12;
        board = new Board(rows, columns);

        assertEquals(-1, board.getLastPiecePosition(-1));
        assertEquals(-1, board.getLastPiecePosition(0));
        assertEquals(-1, board.getLastPiecePosition(4));
        assertEquals(-1, board.getLastPiecePosition(9));
        assertEquals(-1, board.getLastPiecePosition(columns - 1));
        assertEquals(-1, board.getLastPiecePosition(columns));
    }

    /**
     * Get last piece position of valid columns on a custom size board.
     */
    @Test
    public void getLastPiecePositionTest4() {
        int rows = 8;
        int columns = 12;
        board = new Board(rows, columns);

        assertTrue(board.placePiece(0, 1));
        assertTrue(board.placePiece(3, 2));
        assertTrue(board.placePiece(3, 1));
        assertTrue(board.placePiece(3, 2));
        assertTrue(board.placePiece(columns - 1, 1));
        assertTrue(board.placePiece(columns - 1, 1));

        assertEquals(0, board.getLastPiecePosition(0));
        assertEquals(2, board.getLastPiecePosition(3));
        assertEquals(1, board.getLastPiecePosition(columns - 1));
    }

    /**
     * Check if a board is full with a default board which is not full.
     */
    @Test
    public void isFullTest1() {
        board = new Board();

        // empty board
        assertFalse(board.isFull());

        // some pieces
        board.placePiece(0, 1);
        board.placePiece(5, 2);
        board.placePiece(3, 1);
        board.placePiece(3, 1);
        assertFalse(board.isFull());

        // some full rows
        for (int i = 0; i < DEFAULT_ROWS; i++) {
            board.placePiece(0, 1);
            board.placePiece(DEFAULT_COLUMNS - 1, 2);
            board.placePiece(4, 2);
        }
        assertFalse(board.isFull());
    }

    /**
     * Check if a board is full with a default board which is full.
     */
    @Test
    public void isFullTest2() {
        board = new Board();

        for (int r = 0; r < DEFAULT_ROWS; r++) {
            for (int c = 0; c < DEFAULT_COLUMNS; c++) {
                board.placePiece(c, 1);
            }
        }
        assertTrue(board.isFull());
    }

    /**
     * Check if a board is full with a custom size board which is not full.
     */
    @Test
    public void isFullTest3() {
        int rows = 8;
        int columns = 9;
        board = new Board();

        // empty board
        assertFalse(board.isFull());

        // some pieces
        board.placePiece(0, 1);
        board.placePiece(5, 2);
        board.placePiece(3, 1);
        board.placePiece(3, 1);
        assertFalse(board.isFull());

        // some full rows
        for (int i = 0; i < rows; i++) {
            board.placePiece(0, 1);
            board.placePiece(columns - 1, 2);
            board.placePiece(4, 2);
        }
        assertFalse(board.isFull());
    }
    /**
     * Check if a board is full with a custom size board which is full.
     */
    @Test
    public void isFullTest4() {
        int rows = 11;
        int columns = 16;
        board = new Board();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                board.placePiece(c, 2);
            }
        }
        assertTrue(board.isFull());
    }
}
