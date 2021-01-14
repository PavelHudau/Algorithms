package com.pavelhudau.sliderpuzzle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void testHammingWhenOneTileIsOutOfPlace() {
        int[][] tiles = {
                {1, 2, 3},
                {4, 0, 6},
                {7, 8, 5}
        };
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
}
