package com.pavelhudau.sliderpuzzle;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] tiles;
    private int hammingValue;
    private int manhattanValue;
    private TwinGenerator twinGenerator;

    /**
     * Create a board from an n-by-n array of tiles,
     *
     * @param tiles tiles[row][col] = tile at (row, col)
     */
    public Board(int[][] tiles) {
        this(tiles, true);
    }

    private Board(int[][] tiles, boolean preCalculate) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < this.tiles.length; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, this.tiles[i].length);
        }
        if (preCalculate) {
            preCalculate();
        }
    }

    /**
     * Builds string representation of this board.
     *
     * @return string representation of this board
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.tiles.length);
        for (int[] row : this.tiles) {
            builder.append("\n");
            for (int j = 0; j < row.length; j++) {
                builder.append(row[j]);
                if (j < row.length - 1) {
                    builder.append(" ");
                }
            }
        }
        return builder.toString();
    }

    /**
     * Calculates dimention of the Board
     *
     * @return dimension
     */
    public int dimension() {
        return this.tiles.length;
    }

    /**
     * Calculates the Hamming distance between a board and the goal board.
     * Hamming distance is the number of tiles in the wrong position.
     *
     * @return number of tiles out of place
     */
    public int hamming() {
        return this.hammingValue;
    }

    /**
     * Calculates sum of Manhattan distances between tiles and goal.
     * The Manhattan distance between a board and the goal board is
     * the sum of the Manhattan distances (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions.
     *
     * @return Sum of Manhattan distances between tiles and goal
     */
    public int manhattan() {
        return this.manhattanValue;
    }

    /**
     * Calculates whether this board the goal board.
     *
     * @return is this board the goal board?
     */
    public boolean isGoal() {
        return this.hammingValue == 0;
    }

    /**
     * Calculates whether the Board is equal to y.
     * Two boards are equal if they are have the same size and their corresponding tiles are in the same positions.
     * The equals() method is inherited from java.lang.Object, so it must obey all of Java’s requirements.
     *
     * @param y Other board
     * @return is the Board equal to y?
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (this.dimension() != other.dimension()) {
            return false;
        }

        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                if (this.tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Creates an Iterable to walk through all neighboring boards.
     * Depending on the location of the blank square, a board can have 2, 3, or 4 neighbors.
     *
     * @return an Iterable to walk through all neighboring boards.
     */
    public Iterable<Board> neighbors() {
        Board me = this;
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new NeighborsIterator(me);
            }
        };
    }

    /**
     * Creates a board that is obtained by exchanging any pair of tiles.
     *
     * @return a board that is obtained by exchanging any pair of tiles.
     */
    public Board twin() {
        if (this.twinGenerator == null) {
            this.twinGenerator = new TwinGenerator();
        }
        Board twin = this.twinGenerator.next();
        assert this.testForTwin(twin);
        return twin;
    }

    private void preCalculate() {
        this.hammingValue = 0;
        this.manhattanValue = 0;
        int dimension = this.tiles.length;
        for (int i = 0; i < this.tiles.length; i++) {
            int[] row = this.tiles[i];
            int rowShift = i * dimension;
            for (int j = 0; j < this.tiles[i].length; j++) {
                if (row[j] == 0) {
                    continue;
                }

                // hamming
                int inPlaceTileValue = rowShift + j + 1;
                if (inPlaceTileValue != row[j]) {
                    this.hammingValue++;
                }

                // manhattan
                int goalI = (row[j] - 1) / dimension;
                int goalJ = (row[j] - 1) % dimension;
                this.manhattanValue += this.distance(i, j, goalI, goalJ);
            }
        }
    }

    private int distance(int ai, int aj, int bi, int bj) {
        return Math.abs(ai - bi) + Math.abs(aj - bj);
    }

    private int[] findValue(int value) {
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                if (this.tiles[i][j] == value) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private void exchangeTiles(int ai, int aj, int bi, int bj) {
        int tmp = this.tiles[ai][aj];
        this.tiles[ai][aj] = this.tiles[bi][bj];
        this.tiles[bi][bj] = tmp;
    }

    public static void main(String[] args) {
        // Proper unit tests are used instead.
    }

    private boolean testForTwin(Board twin) {
        if (this.equals(twin)) {
            return false;
        }
        int[] thisZero = this.findValue(0);
        int[] twinZero = twin.findValue(0);
        if (thisZero[0] != twinZero[0] || thisZero[1] != twinZero[1]) {
            return false;
        }
        return true;
    }

    private static class NeighborsIterator implements Iterator<Board> {
        private final Board[] neighbors = new Board[4];
        int maxBoardIdx = -1;
        int iteratorIdx = -1;

        NeighborsIterator(Board board) {
            int[] zeroPosition = board.findValue(0);
            if (zeroPosition == null) {
                throw new IllegalStateException("Can't find 0 value");
            }

            int iZero = zeroPosition[0];
            int jZero = zeroPosition[1];

            // Top neighbor
            if (iZero > 0) {
                Board topNeighbor = new Board(board.tiles, false);
                topNeighbor.exchangeTiles(iZero, jZero, iZero - 1, jZero);
                topNeighbor.preCalculate();
                this.maxBoardIdx++;
                this.neighbors[this.maxBoardIdx] = topNeighbor;
            }

            // Bottom neighbor
            if (iZero + 1 < board.dimension()) {
                Board bottomNeighbor = new Board(board.tiles, false);
                bottomNeighbor.exchangeTiles(iZero, jZero, iZero + 1, jZero);
                bottomNeighbor.preCalculate();
                this.maxBoardIdx++;
                this.neighbors[this.maxBoardIdx] = bottomNeighbor;
            }

            // Left neighbor
            if (jZero > 0) {
                Board leftNeighbor = new Board(board.tiles, false);
                leftNeighbor.exchangeTiles(iZero, jZero, iZero, jZero - 1);
                leftNeighbor.preCalculate();
                this.maxBoardIdx++;
                this.neighbors[this.maxBoardIdx] = leftNeighbor;
            }

            // Right neighbor
            if (jZero + 1 < board.dimension()) {
                Board rightNeighbor = new Board(board.tiles, false);
                rightNeighbor.exchangeTiles(iZero, jZero, iZero, jZero + 1);
                rightNeighbor.preCalculate();
                this.maxBoardIdx++;
                this.neighbors[this.maxBoardIdx] = rightNeighbor;
            }
        }

        @Override
        public boolean hasNext() {
            return this.iteratorIdx < this.maxBoardIdx;
        }

        @Override
        public Board next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("There are no more elements.");
            }
            this.iteratorIdx++;
            return this.neighbors[this.iteratorIdx];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }
    }

    private class TwinGenerator {
        private final int twinDeltaA;
        private final int twinDeltaB;
        private int twinIdxA = 0;
        private int twinIdxB = 0;

        public TwinGenerator() {
            int dimension = dimension();
            this.twinDeltaA = StdRandom.uniform(dimension);
            this.twinDeltaB = StdRandom.uniform(dimension);
        }

        public Board next() {
            int dimension = dimension();
            int tileCount = dimension * dimension;

            while (true) {
                this.twinIdxB = (this.twinIdxB + 1) % tileCount;
                if (this.twinIdxB == 0) {
                    this.twinIdxA = (this.twinIdxA + 1) % tileCount;
                    if (this.twinIdxA >= tileCount) {
                        this.twinIdxA = 0;
                    }
                    this.twinIdxB = this.twinIdxA + 1;
                }

                int ai = (this.twinIdxA + this.twinDeltaA) / dimension;
                int aj = (this.twinIdxA + this.twinDeltaA) % dimension;
                if (tiles[ai][aj] == 0) {
                    this.twinIdxA = (this.twinIdxA + 1) % tileCount;
                    ai = (this.twinIdxA + this.twinDeltaA) / dimension;
                    aj = (this.twinIdxA + this.twinDeltaA) % dimension;
                }

                int bi = (this.twinIdxB + this.twinDeltaB) / dimension;
                int bj = (this.twinIdxB + this.twinDeltaB) % dimension;
                if (tiles[bi][bj] == 0 || (bi == ai && bj == aj)) {
                    continue;
                }

                Board twinBoard = new Board(tiles, false);
                twinBoard.exchangeTiles(ai, aj, bi, bj);
                twinBoard.preCalculate();

                return twinBoard;
            }
        }
    }
}