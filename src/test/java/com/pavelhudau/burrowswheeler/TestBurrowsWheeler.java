package com.pavelhudau.burrowswheeler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBurrowsWheeler {
    @Test
    void testTransform() {
        // GIVEN
        BurrowsWheeler bw = new BurrowsWheeler();

        // WHEN
        BurrowsWheeler.TransformResult result = bw.transform("ABRACADABRA!");

        // THEN
        assertEquals(3, result.originalStrIndex);
        char[] expectedTransformedChars = new char[]{'A', 'R', 'D', '!', 'R', 'C', 'A', 'A', 'A', 'A', 'B', 'B'};
        assertArrayEquals(expectedTransformedChars, result.transformedChars);
    }
}
