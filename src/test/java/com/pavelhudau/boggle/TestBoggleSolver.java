package com.pavelhudau.boggle;

import edu.princeton.cs.algs4.In;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoggleSolver {
    private static final String DICTIONARY_ALGS_4_TXT = "dictionary-algs4.txt";

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
    void testScore(String word, int expectedScore) {
        BoggleSolver solver = new BoggleSolver(readDictionary(DICTIONARY_ALGS_4_TXT));
        assertEquals(expectedScore, solver.scoreOf(word));
    }

    private static String[] readDictionary(String fileName) {
        In in = new In("src/main/resources/boggle/" + fileName);
        return in.readAllStrings();
    }
}
