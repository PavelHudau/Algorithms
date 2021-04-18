package com.pavelhudau.boggle;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.TrieSET;

/**
 * Immutable type Boggle solver that finds all valid words in a given Boggle board, using a given dictionary.
 */
public class BoggleSolver {
    private final TraversableTrieSET dictionary;
    private final int maxWordLength;

    private TrieSET foundWords;
    private Graph boardGraph;
    private BoggleBoard board;
    private boolean[] visited;

    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     * (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     */
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new TraversableTrieSET();

        int maxWordLen = 0;
        for (String word : dictionary) {
            if (word.length() > maxWordLen) {
                maxWordLen = word.length();
            }

            this.dictionary.add(word);
        }

        this.maxWordLength = maxWordLen;
    }

    /**
     * Calculates the set of all valid words in the given Boggle board, as an Iterable.
     *
     * @param board Boggle board.
     * @return The set of all valid words in the given Boggle board, as an Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        this.board = board;
        this.boardGraph = this.createBoardGraph();
        this.foundWords = new TrieSET();

        // Run slightly modified DFS from every board tile
        // that allows to re-revit the tile that is being left.
        // This will allow to collect all possible valid tile combinations.
        this.visited = new boolean[this.board.rows() * this.board.cols()];

        // this.maxWordLength + 1 to accomodate special case when 'Q' represents "Qu"
        char[] word = new char[this.maxWordLength + 1];
        for (int r = 0; r < this.board.rows(); r++) {
            for (int c = 0; c < this.board.cols(); c++) {
                this.findAllWordsWithDfs(
                        this.boardIdToVertex(r, c),
                        word,
                        0,
                        this.dictionary.getRoot());
            }
        }

        return () -> this.foundWords.iterator();
    }

    /**
     * Calculates the score of the given word if it is in the dictionary, zero otherwise.
     * (You can assume the word contains only the uppercase letters A through Z.)
     *
     * @param word A word.
     * @return The score of the given word if it is in the dictionary, zero otherwise.
     */
    public int scoreOf(String word) {
        int score = wordScoreByLength(word);
        if (score > 0 && this.dictionary.contains(word)) {
            return score;
        } else {
            return 0;
        }
    }

    private static int wordScoreByLength(String word) {
        int length = word.length();
        if (length < 6) {
            if (length < 3) {
                // 0-2
                return 0;
            }

            if (length < 5) {
                // 3-4
                return 1;
            }

            // 5
            return 2;
        } else {
            if (length > 7) {
                // 8+
                return 11;
            }

            if (length > 6) {
                // 7
                return 5;
            }

            // 6
            return 3;
        }
    }

    private Graph createBoardGraph() {
        Graph graph = new Graph(this.board.rows() * this.board.cols());
        int lastCol = this.board.cols() - 1;
        int lastRow = this.board.rows() - 1;
        for (int r = 0; r <= lastRow; r++) {
            for (int c = 0; c <= lastCol; c++) {
                int currentVertex = this.boardIdToVertex(r, c);
                if (c < lastCol) {
                    // left to right edge
                    graph.addEdge(currentVertex, this.boardIdToVertex(r, c + 1));
                }

                if (r < lastRow) {
                    // top to bottom edge
                    graph.addEdge(currentVertex, this.boardIdToVertex(r + 1, c));
                    if (c > 0) {
                        // diagonal to bottom left
                        graph.addEdge(currentVertex, this.boardIdToVertex(r + 1, c - 1));
                    }

                    if (c < lastCol) {
                        // diagonal to bottom left
                        graph.addEdge(currentVertex, this.boardIdToVertex(r + 1, c + 1));
                    }
                }
            }
        }

        return graph;
    }

    private void findAllWordsWithDfs(int vertex, char[] word, int currentCharIdx, TraversableTrieSET.Node currentDictionaryNode) {
        char currentTile = this.getBoardTileByVertex(vertex);
        currentDictionaryNode = currentDictionaryNode.getNext(currentTile);
        if (currentDictionaryNode == null) {
            // Dictionary doesn't have prefix, so there is no reason to continue.
            return;
        }

        word[currentCharIdx] = currentTile;

        // Special "Qu" case.
        // In the English language, the letter Q is almost always followed by the letter U. Consequently, the side of
        // one die is printed with the two-letter sequence Qu instead of Q (and this two-letter sequence must be used
        // together when forming words). When scoring, Qu counts as two letters; for example, the word QuEUE scores as
        // a 5-letter word even though it is formed by following a sequence of only 4 dice.
        if (currentTile == 'Q') {
            currentDictionaryNode = currentDictionaryNode.getNext('U');
            if (currentDictionaryNode == null) {
                // Dictionary doesn't have prefix, so there is no reason to continue.
                return;
            }

            word[++currentCharIdx] = 'U';
        }

        if (currentCharIdx + 1 == this.maxWordLength) {
            // Current word is longer than longest word in the dictionary, hence the word is definitely not in the
            // dictionary, therefore there is no reason to continue.
            return;
        }

        // If the word is in the dictionary and it's length is greater than 2.
        // We exclude words with length of 2 or less because their score is 0;
        if (currentDictionaryNode.isDictionaryWord() && currentCharIdx > 1) {
            String currentWord = String.copyValueOf(word, 0, currentCharIdx + 1);
            this.foundWords.add(currentWord);
        }

        this.visited[vertex] = true;
        for (int w : this.boardGraph.adj(vertex)) {
            if (!this.visited[w]) {
                this.findAllWordsWithDfs(w, word, currentCharIdx + 1, currentDictionaryNode);
            }
        }

        // Release visited flag to vertex to allow discovery of all
        // possible valid board tile combinations.
        this.visited[vertex] = false;
    }

    private int boardIdToVertex(int row, int col) {
        return row * this.board.cols() + col;
    }

    private char getBoardTileByVertex(int vertex) {
        int col = vertex % this.board.cols();
        int row = vertex / this.board.cols();
        return this.board.getLetter(row, col);
    }
}
