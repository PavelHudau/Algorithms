package com.pavelhudau.boggle;

import edu.princeton.cs.algs4.TrieSET;

/**
 * Immutable type Boggle solver that finds all valid words in a given Boggle board, using a given dictionary.
 */
public class BoggleSolver {
    private final TrieSET dictionary;

    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     * (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     */
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new TrieSET();
        for (String word : dictionary) {
            this.dictionary.add(word);
        }
    }

    /**
     * Calculates the set of all valid words in the given Boggle board, as an Iterable.
     *
     * @param board Boggle board.
     * @return The set of all valid words in the given Boggle board, as an Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        throw new IllegalArgumentException("NOT IMPLEMENTED");
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)

    /**
     * Calculates the score of the given word if it is in the dictionary, zero otherwise.
     * (You can assume the word contains only the uppercase letters A through Z.)
     *
     * @param word A word.
     * @return The score of the given word if it is in the dictionary, zero otherwise.
     */
    public int scoreOf(String word) {
        if (this.dictionary.contains(word)) {
            return existingWordScore(word);
        } else {
            return 0;
        }
    }

    private static int existingWordScore(String word) {
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
        }
        else {
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
}
