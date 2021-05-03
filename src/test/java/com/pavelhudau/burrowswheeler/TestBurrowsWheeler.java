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

    @Test
    void testInverseTransform() {
        // GIVEN
        BurrowsWheeler bw = new BurrowsWheeler();
        char[] transformedChars = new char[]{'A', 'R', 'D', '!', 'R', 'C', 'A', 'A', 'A', 'A', 'B', 'B'};

        // WHEN
        char[] inverseTransformedChars = bw.inverseTransform(3, transformedChars);

        // THEN
        char[] expectedInverseTransformedChars = new char[]{'A', 'B', 'R', 'A', 'C', 'A', 'D', 'A', 'B', 'R', 'A', '!'};
        assertArrayEquals(expectedInverseTransformedChars, inverseTransformedChars);
    }

    @Test
    void testTransformWhenAllCharactersAreSame() {
        // GIVEN
        BurrowsWheeler bw = new BurrowsWheeler();

        // WHEN
        BurrowsWheeler.TransformResult result = bw.transform("AAA");

        // THEN
        assertEquals(0, result.originalStrIndex);
        char[] expectedTransformedChars = new char[]{'A', 'A', 'A'};
        assertArrayEquals(expectedTransformedChars, result.transformedChars);
    }

    @Test
    void testInverseTransformWhenAllCharactersAreSame() {
        // GIVEN
        BurrowsWheeler bw = new BurrowsWheeler();
        char[] transformedChars = new char[]{'A', 'A', 'A'};

        // WHEN
        char[] inverseTransformedChars = bw.inverseTransform(0, transformedChars);

        // THEN
        char[] expectedInverseTransformedChars = new char[]{'A', 'A', 'A'};
        assertArrayEquals(expectedInverseTransformedChars, inverseTransformedChars);
    }

    @Test
    void testTransformWhenOnlySingleChar() {
        // GIVEN
        BurrowsWheeler bw = new BurrowsWheeler();

        // WHEN
        BurrowsWheeler.TransformResult result = bw.transform("A");

        // THEN
        assertEquals(0, result.originalStrIndex);
        char[] expectedTransformedChars = new char[]{'A'};
        assertArrayEquals(expectedTransformedChars, result.transformedChars);
    }

    @Test
    void testInverseTransformWhenOnlySingleChar() {
        // GIVEN
        BurrowsWheeler bw = new BurrowsWheeler();
        char[] transformedChars = new char[]{'A'};

        // WHEN
        char[] inverseTransformedChars = bw.inverseTransform(0, transformedChars);

        // THEN
        char[] expectedInverseTransformedChars = new char[]{'A'};
        assertArrayEquals(expectedInverseTransformedChars, inverseTransformedChars);
    }
}
