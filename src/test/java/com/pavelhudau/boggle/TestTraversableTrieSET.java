package com.pavelhudau.boggle;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTraversableTrieSET {
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
}
