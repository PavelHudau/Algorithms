package com.pavelhudau.boggle;

import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTraversableTrieSET {
    private static final String RESOURCES_PATH = "src/main/resources/boggle/";
    private static final String DICTIONARY_ZINGARELLI_2005 = "dictionary-zingarelli2005.txt";

    @Test
    void testGetRootWhenNoWordsAdded() {
        TraversableTrieSET trieSet = new TraversableTrieSET();
        assertNull(trieSet.getRoot());
    }

    @Test
    void testGetRootWhenWordsAreAdded() {
        // GIVEN
        TraversableTrieSET trieSet = new TraversableTrieSET();
        // WHEN
        trieSet.add("WORD");
        //THEN
        assertNotNull(trieSet.getRoot());
    }

    @Test
    void testAddAndContains() {
        // GIVEN
        TraversableTrieSET trieSet = new TraversableTrieSET();
        List<String> words = Arrays.asList(
                "A",
                "AA",
                "B",
                "BCC",
                "DDD",
                "D",
                "TEST"
        );

        // WHEN
        for (String word : words) {
            trieSet.add(word);
        }

        //THEN
        for (String word : words) {
            assertTrue(trieSet.contains(word));
        }

        assertFalse(trieSet.contains("Z"));
        assertFalse(trieSet.contains("T"));
        assertFalse(trieSet.contains("DD"));
    }

    @Test
    void testAddAndContainsWithLargeDictionary() {
        // GIVEN
        TraversableTrieSET trieSet = new TraversableTrieSET();

        // WHEN
        for (String word : readDictionary(DICTIONARY_ZINGARELLI_2005)) {
            trieSet.add(word);
        }

        // THEN
        assertTrue(trieSet.contains("ZWINGLISTI"));
        assertTrue(trieSet.contains("ZZZ"));
        assertTrue(trieSet.contains("ABACA"));
        assertFalse(trieSet.contains("AAA"));
        assertFalse(trieSet.contains("ZZZZ"));

    }

    private static String[] readDictionary(String fileName) {
        In in = new In(RESOURCES_PATH + fileName);
        return in.readAllStrings();
    }
}
