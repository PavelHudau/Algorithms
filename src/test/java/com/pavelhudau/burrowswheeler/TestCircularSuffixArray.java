package com.pavelhudau.burrowswheeler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestCircularSuffixArray {
    @Test
    void testConstructorWhenParameterIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new CircularSuffixArray(null));
    }

    @ParameterizedTest
    @CsvSource({
            "Z, 1",
            "AL, 2",
    })
    void testLength(String str, int expectedLength) {
        CircularSuffixArray csa = new CircularSuffixArray(str);
        assertEquals(expectedLength, csa.length());
    }

    @Test
    void testIndex() {
        // GIVEN
        String str = "ABRACADABRA!";
        // WHEN
        CircularSuffixArray csa = new CircularSuffixArray(str);
        // THEN
        int[] expectedIndices = {11, 10, 7, 0, 3, 5, 8, 1, 4, 6, 9, 2};
        for (int i = 0; i < expectedIndices.length; i++) {
            assertEquals(expectedIndices[i], csa.index(i));
        }
    }
}
