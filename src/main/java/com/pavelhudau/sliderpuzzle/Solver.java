package com.pavelhudau.sliderpuzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {
    private static final int UNSOLVABLE = -1;
    private Board[] boardSolution;

    /**
     * Find a solution to the initial board (using the A* algorithm).
     *
     * @param initial Board to solve.
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial can not be null");
        }

        MinPQ<SearchNode> minPq = new MinPQ<>();
        minPq.insert(new SearchNode(initial, 0, null));

        MinPQ<SearchNode> minPqTwin = new MinPQ<>();
        minPqTwin.insert(new SearchNode(initial.twin(), 0, null));

        while (!isSolvedOrShouldStop(minPq) && !isSolvedOrShouldStop(minPqTwin)) {
            addNeighbors(minPq);
            // If twin board is solved first, then initial board does not have a solution.
            addNeighbors(minPqTwin);
        }

        this.saveSolution(minPq);
    }

    private static boolean isSolvedOrShouldStop(MinPQ<SearchNode> minPq) {
        return minPq.isEmpty() || isSolved(minPq);
    }

    private static boolean isSolved(MinPQ<SearchNode> minPq) {
        return minPq.min().board.isGoal();
    }

    private static void addNeighbors(MinPQ<SearchNode> minPq) {
        SearchNode node = minPq.delMin();
        for (Board neighbor : node.board.neighbors()) {
            if (node.previous != null && neighbor.equals(node.previous.board)) {
                // Optimization not to insert nodes that have already been looked at.
                continue;
            }
            minPq.insert(new SearchNode(neighbor, node.steps + 1, node));
        }
    }

    /**
     * Is the initial board solvable? (see below).
     *
     * @return whether the initial board solvable? (see below).
     */
    public boolean isSolvable() {
        return this.boardSolution != null;
    }

    /**
     * Gets min number of moves to solve initial board.
     *
     * @return min number of moves to solve initial board; -1 if unsolvable.
     */
    public int moves() {
        return this.boardSolution == null ? UNSOLVABLE : this.boardSolution.length;
    }

    /**
     * Gets sequence of boards in a shortest solution; null if unsolvable.
     *
     * @return sequence of boards in a shortest solution; null if unsolvable.
     */
    public Iterable<Board> solution() {
        if (this.boardSolution == null) {
            return null;
        }

        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new Solver.SolutionIterator();
            }
        };
    }

    public static void main(String[] args) {
        // Proper unit tests are used instead.
    }

    private void saveSolution(MinPQ<SearchNode> minPq) {
        if (minPq.isEmpty()) {
            return;
        }

        if (isSolved(minPq)) {
            this.boardSolution = new Board[this.countSolutionMoves(minPq)];
            int insertIdx = this.boardSolution.length - 1;
            SearchNode node = minPq.min();
            while (node != null) {
                this.boardSolution[insertIdx] = node.board;
                node = node.previous;
                insertIdx--;
            }
        }
    }

    private int countSolutionMoves(MinPQ<SearchNode> minPq) {
        int size = 0;
        SearchNode node = minPq.min();
        while (node != null) {
            size++;
            node = node.previous;
        }

        return size;
    }

    private static class SearchNode implements Comparable {
        private final Board board;
        private final int steps;
        private final SearchNode previous;

        private SearchNode(Board board, int steps, SearchNode previous) {
            this.board = board;
            this.steps = steps;
            this.previous = previous;
        }

        @Override
        public int compareTo(Object other) {
            if (other == null) {
                throw new NullPointerException("o can not be null");
            }

            if (this == other) {
                return 0;
            }

            if (this.getClass() != other.getClass()) {
                throw new ClassCastException("Can't compare " + this.getClass() + " and " + other.getClass());
            }

            return compareToManhattan((SearchNode) other);
        }

//        private int compareToHamming(SearchNode other) {
//            int hammingMyPriority = this.board.hamming() + this.steps;
//            int hammingOtherPriority = other.board.hamming() + other.steps;
//            return hammingMyPriority - hammingOtherPriority;
//        }

        private int compareToManhattan(SearchNode other) {
            int manhattanMyPriority = this.board.manhattan() + this.steps;
            int manhattanOtherPriority = other.board.manhattan() + other.steps;
            return manhattanMyPriority - manhattanOtherPriority;
        }

        @Override
        public String toString() {
            return "Steps: " + this.steps + ", Board: " + this.board.toString();
        }
    }

    private class SolutionIterator implements Iterator<Board> {
        int currentIdx = 0;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return boardSolution.length > currentIdx;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Board next() {
            if (boardSolution.length > currentIdx) {
                Board current = boardSolution[this.currentIdx];
                currentIdx++;
                return current;
            } else {
                throw new NoSuchElementException("There are no more elements.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }
    }
}
