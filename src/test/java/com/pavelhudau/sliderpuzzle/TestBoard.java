package com.pavelhudau.sliderpuzzle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoard {
    @Test
    void testToString() {
        int[][] tiles = {
                {1, 2, 3},
                {4, 5, 6},
                {0, 7, 8}
        };
        Board board = new Board(tiles);
        assertEquals("3\n1 2 3\n4 5 6\n0 7 8", board.toString());
    }

    @Test
    void testDimensions() {
        int[][] tiles = {
                {1, 2, 3},
                {4, 5, 6},
                {0, 7, 8}
        };
        Board board = new Board(tiles);
        assertEquals(3, board.dimension());
    }

    @Test
    void testHammingWhenAllTilesInPlace() {
        int[][] tiles = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(tiles);
        assertEquals(0, board.hamming());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0, 1",
            "0, 2",
            "1, 0",
            "1, 1",
            "1, 2",
            "2, 0",
            "2, 1"
    })
    void testHammingWhenOneTileIsOutOfPlace(int iOutOfPlace, int jOutOfPlace) {
        int[][] tiles = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        tiles[2][2] = tiles[iOutOfPlace][jOutOfPlace];
        tiles[iOutOfPlace][jOutOfPlace] = 0;
        Board board = new Board(tiles);
        assertEquals(1, board.hamming());
    }

    @Test
    void testHammingWhenAllTilesAreOutOfPlace() {
        int[][] tiles = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board board = new Board(tiles);
        assertEquals(8, board.hamming());
    }

    @Test
    void testHammingWhenSomeTilesAreOutOfPlace() {
        int[][] tiles = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board board = new Board(tiles);
        assertEquals(5, board.hamming());
    }

    @Test
    void testManhattanWhenAllTilesInPlace() {
        int[][] tiles = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(tiles);
        assertEquals(0, board.manhattan());
    }

    @Test
    void testManhattanWhenSomeTilesAreOutOfPlace() {
        int[][] tiles = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board board = new Board(tiles);
        assertEquals(10, board.manhattan());
    }

    @Test
    void testManhattanWhenAllTilesAreOutOfPlace() {
        int[][] tiles = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board board = new Board(tiles);
        assertEquals(12, board.manhattan());
    }

    @Test
    void testIsGoalWhenBoardIsGoal() {
        int[][] tiles = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(tiles);
        assertTrue(board.isGoal());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0, 1",
            "0, 2",
            "1, 0",
            "1, 1",
            "1, 2",
            "2, 0",
            "2, 1"
    })
    void testIsGoalWhenBoardIsNotGoal(int iNotGoal, int jNotGoal) {
        int[][] tiles = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        tiles[2][2] = tiles[iNotGoal][jNotGoal];
        tiles[iNotGoal][jNotGoal] = 0;
        Board board = new Board(tiles);
        assertFalse(board.isGoal());
    }

    @Test
    void testEqualsWhenSameInstance() {
        int[][] tiles = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board boardA = new Board(tiles);
        assertTrue(boardA.equals(boardA));
    }

    @Test
    void testEqualsWhenBoardsAreEqual() {
        int[][] tiles = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board boardA = new Board(tiles);
        Board boardB = new Board(tiles);
        assertTrue(boardA.equals(boardB));
    }

    @Test
    void testEqualsWhenComparingToNull() {
        int[][] tiles = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board boardA = new Board(tiles);
        assertFalse(boardA.equals(null));
    }

    @Test
    void testEqualsWhenComparingToNonBoardObject() {
        int[][] tiles = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board boardA = new Board(tiles);
        assertFalse(boardA.equals(new Object()));
    }

    @Test
    void testEqualsWhenDimensionsAreDifferent() {
        int[][] tilesA = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        Board boardA = new Board(tilesA);
        int[][] tilesB = {
                {0, 1},
                {2, 3}
        };
        Board boardB = new Board(tilesB);
        assertFalse(boardA.equals(boardB));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0, 1",
            "0, 2",
            "1, 0",
            "1, 1",
            "1, 2",
            "2, 0",
            "2, 1"
    })
    void testEqualsWhenBoardsAreDifferent(int iDifferent, int jDifferent) {
        // GIVEN
        int[][] tilesA = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board boardA = new Board(tilesA);
        int[][] tilesB = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        tilesB[2][2] = tilesB[iDifferent][jDifferent];
        tilesB[iDifferent][jDifferent] = 0;
        // WHEN
        Board boardB = new Board(tilesB);
        // THEN
        assertFalse(boardA.equals(boardB));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 2",
            "0, 1, 3",
            "0, 2, 2",
            "1, 0, 3",
            "1, 1, 4",
            "1, 2, 3",
            "2, 0, 2",
            "2, 1, 3",
            "2, 2, 2"
    })
    void testNeighbors(int iZero, int jZero, int expectedNeighborCount) {
        // GIVEN
        // Make a goal board
        int[][] tiles = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        // Swap one element with Zero
        tiles[2][2] = tiles[iZero][jZero];
        tiles[iZero][jZero] = 0;
        Board board = new Board(tiles);
        int neighborCount = 0;
        // WHEN
        for (Board neighbor : board.neighbors()) {
            // THEN
            neighborCount++;
            assertFalse(neighbor.equals(board));
        }
        assertEquals(expectedNeighborCount, neighborCount);
    }

    @Test
    void testTwin() {
        // NOTE: the test is not consistent as it tries to validate
        // behavior that is based on randomness.
        // The test makes hundred attempts to create a duplicated twin board.
        // Twin board should not be equal to original board.
        for (int i = 0; i < 100; i++) {
            int[][] tiles = {
                    {1, 2},
                    {3, 0}
            };
            Board board = new Board(tiles);
            assertFalse(board.equals(board.twin()));
        }
    }
}
