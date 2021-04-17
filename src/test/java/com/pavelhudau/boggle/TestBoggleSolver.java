package com.pavelhudau.boggle;

import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoggleSolver {
    private static final String RESOURCES_PATH = "src/main/resources/boggle/";
    private static final String DICTIONARY_ALGS_4_TXT = "dictionary-algs4.txt";
    private static final String BOARD_4_X_4 = "board4x4.txt";
    private static BoggleSolver dictAlg4Solver;


    @ParameterizedTest
    @CsvSource({
            "Z, 0",
            "AL , 0",
            "ACT,1",
            "ALSO, 1",
            "AMINO, 2",
            "ANALOG, 3",
            "ANALOGY, 5",
            "ANALYSES, 11",
            "ANTISYMMETRIC, 11"

    })
    void testScoreWhenWordExists(String word, int expectedScore) {
        BoggleSolver solver = getDictAlg4Solver();
        assertEquals(expectedScore, solver.scoreOf(word));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "z",
            "al",
            "act",
            "also",
            "amino",
            "analog",
            "analogy",
            "analyses",
            "antisymmetric"
    })
    void testScoreWhenWordDoesNotExist(String word) {
        int expectedScore = 0;
        BoggleSolver solver = getDictAlg4Solver();
        assertEquals(expectedScore, solver.scoreOf(word));
    }

    @Test
    void testGetAllValidWords() {
        BoggleSolver solver = getDictAlg4Solver();
        Iterable<String> words = solver.getAllValidWords(getBoggleBoard4x4());
        assertNotNull(words);

    }

    private static BoggleSolver getDictAlg4Solver() {
        if (dictAlg4Solver == null) {
            dictAlg4Solver = new BoggleSolver(readDictionary(DICTIONARY_ALGS_4_TXT));
        }

        return dictAlg4Solver;
    }

    private static String[] readDictionary(String fileName) {
        In in = new In(RESOURCES_PATH + fileName);
        return in.readAllStrings();
    }

    private static BoggleBoard getBoggleBoard4x4() {
        return new BoggleBoard(RESOURCES_PATH + BOARD_4_X_4);
    }
}
