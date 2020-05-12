package model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class VierGewinntTest {

    /* VierGewinnt Specification:
     *
     * // constructor
     * ----- Board()
     * ----- Board(columns: int, rows: int)
     *
     * // change the state
     * ----- placePiece(column: int): boolean
     * ----- placePiece(column: int, piece: int): boolean
     *
     * // 'read' the board
     * ----- getField(column: int, row: int): int
     * ----- getColumns(): int
     * ----- getRows(): int
     * ----- getCurrentPlayer(): int
     *
     * // get win info
     * ----- getWinninPieces(): int[][]
     * ----- getWinningPlayer(): int // not needed
     *
     * // check the state
     * ----- hasEnded(): boolean
     *
     */

    private static final int DEFAULT_COLUMNS = 7;
    private static final int DEFAULT_ROWS = 6;

    /**
     * Tests the default VierGewinnt constructor initialization.
     */
    @Test
    public void VierGewinntTest1() {
        VierGewinnt game = new VierGewinnt();

        assertEquals(DEFAULT_COLUMNS, game.getColumns());
        assertEquals(DEFAULT_ROWS, game.getRows());
        for (int x = 0; x < game.getColumns(); x++) {
            for (int y = 0; y < game.getRows(); y++) {
                assertEquals(0, game.getField(x, y));
            }
        }
    }

    /**
     * Tests a custom size VierGewinnt constructor initialization.
     */
    @Test
    public void VierGewinntTest2() {
        int columns = 5;
        int rows = 8;
        VierGewinnt game = new VierGewinnt(columns, rows);

        assertEquals(columns, game.getColumns());
        assertEquals(rows, game.getRows());
        for (int x = 0; x < game.getColumns(); x++) {
            for (int y = 0; y < game.getRows(); y++) {
                assertEquals(0, game.getField(x, y));
            }
        }
    }

    /**
     * Tests placing different pieces in a default game.
     * The pieces are placed legally and alternately.
     */
    @Test
    public void placePieceTest1() {
        VierGewinnt game = new VierGewinnt();

        assertTrue(game.placePiece(4, 1));
        assertEquals(1, game.getField(4, 0));
        assertTrue(game.placePiece(4, 2));
        assertEquals(2, game.getField(4, 1));
        assertTrue(game.placePiece(0, 1));
        assertEquals(1, game.getField(0, 0));
        assertTrue(game.placePiece(1, 2));
        assertEquals(2, game.getField(1, 0));
        assertTrue(game.placePiece(0, 1));
        assertEquals(1, game.getField(0, 1));
        assertTrue(game.placePiece(0, 2));
        assertEquals(2, game.getField(0, 2));
        assertTrue(game.placePiece(DEFAULT_COLUMNS - 1, 1));
        assertEquals(1, game.getField(DEFAULT_COLUMNS - 1, 0));
    }

    /**
     * Tests placing different pieces in a custom size game.
     * The pieces are placed legally and alternately.
     */
    @Test
    public void placePieceTest2() {
        int columns = 3;
        int rows = 4;
        VierGewinnt game = new VierGewinnt();

        assertTrue(game.placePiece(columns - 1, 1));
        assertEquals(1, game.getField(columns - 1, 0));
        assertTrue(game.placePiece(columns - 1, 2));
        assertEquals(2, game.getField(columns - 1, 1));
        assertTrue(game.placePiece(0, 1));
        assertEquals(1, game.getField(0, 0));
        assertTrue(game.placePiece(1, 2));
        assertEquals(2, game.getField(1, 0));
        assertTrue(game.placePiece(0, 1));
        assertEquals(1, game.getField(0, 1));
        assertTrue(game.placePiece(0, 2));
        assertEquals(2, game.getField(0, 2));
    }

    /**
     * Tests, whether the game automatically alternates players when placing pieces.
     */
    @Test
    public void placePieceTest3() {
        VierGewinnt game = new VierGewinnt();

        assertTrue(game.placePiece(0));
        assertEquals(1, game.getField(0, 0));
        assertTrue(game.placePiece(0));
        assertEquals(2, game.getField(0, 1));
        assertTrue(game.placePiece(0));
        assertEquals(1, game.getField(0, 2));
        assertTrue(game.placePiece(2));
        assertEquals(2, game.getField(2, 0));
        assertTrue(game.placePiece(1));
        assertEquals(1, game.getField(1, 0));
        assertTrue(game.placePiece(2));
        assertEquals(2, game.getField(2, 1));
    }

    /**
     * Tests placing pieces illegally. Tests placing pieces outside the playing field.
     */
    @Test
    public void placePieceTest4() {
        VierGewinnt game = new VierGewinnt();

        int currentPlayer = game.getCurrentPlayer();
        assertFalse(game.placePiece(-1));
        assertEquals(currentPlayer, game.getCurrentPlayer());
        assertFalse(game.placePiece(9));
        assertEquals(currentPlayer, game.getCurrentPlayer());

        assertFalse(game.placePiece(-1, currentPlayer));
        assertEquals(currentPlayer, game.getCurrentPlayer());
        assertFalse(game.placePiece(7, currentPlayer));
        assertEquals(currentPlayer, game.getCurrentPlayer());

        // place some pieces
        for (int i = 0; i < game.getRows(); i++) {
            game.placePiece(0);
            game.placePiece(1);
            game.placePiece(2);
        }

        currentPlayer = game.getCurrentPlayer();
        assertFalse(game.placePiece(0));
        assertEquals(currentPlayer, game.getCurrentPlayer());
        assertFalse(game.placePiece(1));
        assertEquals(currentPlayer, game.getCurrentPlayer());

        assertFalse(game.placePiece(0, currentPlayer));
        assertEquals(currentPlayer, game.getCurrentPlayer());
        assertFalse(game.placePiece(2, currentPlayer));
        assertEquals(currentPlayer, game.getCurrentPlayer());
    }

    /**
     * Tests placing pieces illegally. The the same piece is placed repeatedly.
     */
    @Test
    public void placePieceTest5() {
        VierGewinnt game = new VierGewinnt();

        int currentPlayer = game.getCurrentPlayer();
        assertTrue(game.placePiece(0, currentPlayer));
        assertNotEquals(currentPlayer, game.getCurrentPlayer());

        assertFalse(game.placePiece(0, currentPlayer));
        assertNotEquals(currentPlayer, game.getCurrentPlayer());
        assertFalse(game.placePiece(1, currentPlayer));
        assertNotEquals(currentPlayer, game.getCurrentPlayer());
    }

    /**
     * Tests placing pieces after the game has ended.
     */
    @Test
    public void placePieceTest6() {
        VierGewinnt game = new VierGewinnt();

        assertTrue(game.placePiece(0));
        assertTrue(game.placePiece(1));
        assertTrue(game.placePiece(0));
        assertTrue(game.placePiece(2));
        assertTrue(game.placePiece(0));
        assertTrue(game.placePiece(3));
        assertTrue(game.placePiece(0)); // player 1 wins

        assertFalse(game.placePiece(3, 1));
        assertFalse(game.placePiece(0, 2));
        assertFalse(game.placePiece(0));
    }

    /**
     * Tests if the game is over with only a few pieces placed and no player having won yet.
     */
    @Test
    public void hasEndedTest1() {
        VierGewinnt game = new VierGewinnt();

        assertFalse(game.hasEnded());
        game.placePiece(0);
        assertFalse(game.hasEnded());
        game.placePiece(1);
        assertFalse(game.hasEnded());
        game.placePiece(2);
        assertFalse(game.hasEnded());
        game.placePiece(0);
        assertFalse(game.hasEnded());
    }

    /**
     * Tests the if the game has ended with some full and some empty columns and no player having won yet.
     */
    @Test
    public void hasEndedTest2() {
        VierGewinnt game = new VierGewinnt();

        for (int i = 0; i < game.getRows(); i++) {
            game.placePiece(0);
            game.placePiece(2);
            game.placePiece(DEFAULT_COLUMNS - 1);
        }
        assertFalse(game.hasEnded());
    }

    /**
     * Tests the return value on a full board (default and custom size).
     */
    @Test
    public void hasEndedTest3() {
        /* full and draw
         * 1, 1, 2, 2, 1, 2, 2
         * 2, 1, 2, 2, 2, 1, 1
         * 1, 2, 1, 1, 1, 2, 2
         * 1, 2, 2, 2, 1, 2, 1
         * 2, 2, 1, 1, 2, 2, 1
         * 1, 1, 2, 1, 1, 1, 2
         *
         * translates to the placing order of:
         */
        int[] placedPieces = {0, 2, 1, 0, 3, 1, 4, 4, 5, 5, 2, 6, 6, 1, 3, 3, 3, 3, 0, 2, 2, 2, 0, 0, 0, 1, 1, 2, 1, 3, 4, 5, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6};
        VierGewinnt game = new VierGewinnt();
        for (int piece : placedPieces) {
            assertFalse(game.hasEnded());
            assertTrue(game.placePiece(piece));
        }
        assertTrue(game.hasEnded());

        /* full and draw
         * 1, 2, 1, 2, 1, 2, 1
         * 1, 1, 2, 2, 1, 2, 2
         * 2, 1, 2, 2, 2, 1, 1
         * 1, 2, 1, 1, 1, 2, 2
         * 1, 2, 2, 2, 1, 2, 1
         * 2, 2, 1, 1, 2, 2, 1
         * 1, 1, 2, 1, 1, 1, 2
         *
         * translates to the placing order of:
         */
        int[] customPlacedPieces = {0, 2, 1, 0, 3, 1, 4, 4, 5, 5, 2, 6, 6, 1, 3, 3, 3, 3, 0, 2, 2, 2, 0, 0, 0, 1, 1, 2, 1, 3, 4, 5, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 0, 1, 2, 3, 4, 5, 6};
        game = new VierGewinnt(7, 7);
        for (int piece : customPlacedPieces) {
            assertFalse(game.hasEnded());
            assertTrue(game.placePiece(piece));
        }
        assertTrue(game.hasEnded());
    }

    /**
     * Checks if the game ends if one player wins and the board is not yet full.
     */
    @Test
    public void hasEndedTest4() {
        VierGewinnt game = new VierGewinnt();

        game.placePiece(0);
        game.placePiece(1);
        game.placePiece(0);
        game.placePiece(1);
        game.placePiece(0);
        game.placePiece(2);
        game.placePiece(0);
        assertTrue(game.hasEnded());

        game = new VierGewinnt();
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(2);
        game.placePiece(2);
        game.placePiece(2);
        game.placePiece(3);
        assertTrue(game.hasEnded());

        game = new VierGewinnt();
        game.placePiece(3);
        game.placePiece(3);
        game.placePiece(3);
        game.placePiece(3);
        game.placePiece(3);
        game.placePiece(0);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(2);
        game.placePiece(2);
        game.placePiece(2);
        assertTrue(game.hasEnded());
    }

    /**
     * Tests the method getWinningPlayer. Checks if a default 'undefined' value is returned
     * when the game is not yet over.
     */
    @Test
    public void getWinningPlayerTest1() {
        VierGewinnt game = new VierGewinnt();

        assertEquals(-1, game.getWinningPlayer());
        game.placePiece(4);
        assertEquals(-1, game.getWinningPlayer());
        game.placePiece(3);
        assertEquals(-1, game.getWinningPlayer());
    }

    /**
     * Tests whether the player that won the game is returned.
     */
    @Test
    public void getWinningPlayerTest2() {
        VierGewinnt game = new VierGewinnt();
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(2);
        game.placePiece(2);
        game.placePiece(2);
        game.placePiece(3);
        assertEquals(2, game.getWinningPlayer());

        game = new VierGewinnt();
        game.placePiece(3);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(2);
        game.placePiece(2);
        game.placePiece(2);
        assertEquals(1, game.getWinningPlayer());

    }

    /**
     * Tests the return value when a draw occurs.
     */
    @Test
    public void getWinningPlayerTest3() {
        /* full and draw
         * 1, 1, 2, 2, 1, 2, 2
         * 2, 1, 2, 2, 2, 1, 1
         * 1, 2, 1, 1, 1, 2, 2
         * 1, 2, 2, 2, 1, 2, 1
         * 2, 2, 1, 1, 2, 2, 1
         * 1, 1, 2, 1, 1, 1, 2
         *
         * translates to the placing order of:
         */
        int[] placedPieces = {0, 2, 1, 0, 3, 1, 4, 4, 5, 5, 2, 6, 6, 1, 3, 3, 3, 3, 0, 2, 2, 2, 0, 0, 0, 1, 1, 2, 1, 3, 4, 5, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6};
        VierGewinnt game = new VierGewinnt();

        assertEquals(-1, game.getWinningPlayer());
        for (int piece : placedPieces) {
            assertTrue(game.placePiece(piece));
        }
        assertEquals(0, game.getWinningPlayer());
    }

    /**
     * Tests the winning pieces if a game is not yet over or the game is a draw.
     */
    @Test
    public void getWinningPiecesTest1() {
        VierGewinnt game = new VierGewinnt();

        assertEquals(0, game.getWinningPieces().length);
        game.placePiece(0);
        game.placePiece(1);
        game.placePiece(2);
        assertEquals(0, game.getWinningPieces().length);
    }

    /**
     * Tests the winning pieces if a game has ended.
     */
    @Test
    public void getWinningPiecesTest2() {
        VierGewinnt game = new VierGewinnt();
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(0);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(1);
        game.placePiece(2);
        game.placePiece(2);
        game.placePiece(2);
        game.placePiece(3);

        int[][] winningPieces = game.getWinningPieces();
        // check array size
        assertEquals(4, winningPieces.length);
        assertEquals(2, winningPieces[0].length);
        // check array content
        int[][] comparePieces = {{0, 3}, {1, 2}, {2, 1}, {3, 0}};
        assertTrue(comparePieces(winningPieces, comparePieces));

    }

    /**
     * Compares two two-dimensional int arrays by sorting them and then comparing each positions.
     * @param a The first array.
     * @param b The second array.
     * @return If the arrays match.
     */
    private boolean comparePieces(int[][] a, int[][] b) {
        // specify sorting of arrays
        Comparator<int[]> c = (first, second) -> {
            if (first.length < second.length) {
                return -1;
            } else if (first.length > second.length) {
                return 1;
            } else {
                for (int i = 0; i < first.length; i++) {
                    if (first[i] != second[i]) {
                        return second[i] - first[i];
                    }
                }
                return 0;
            }
        };

        // sort each array
        Arrays.sort(a, c);
        Arrays.sort(b, c);

        // compare arrays
        if (a.length == b.length) {
            for (int x = 0; x < a.length; x++) {
                if (a[x].length == b[x].length) {
                    for (int y = 0; y < a[x].length; y++) {
                        if (a[x][y] != b[x][y]) {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
