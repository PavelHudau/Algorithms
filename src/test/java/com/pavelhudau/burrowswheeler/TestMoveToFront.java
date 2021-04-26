package com.pavelhudau.burrowswheeler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMoveToFront {

    @Test
    void testEncodeAndDecode() {
        // GIVEN
        String initialStr = "ABRACADABRA!";
        MoveToFront encoder = new MoveToFront();
        MoveToFront decoder = new MoveToFront();

        // WHEN
        char[] encoded = new char[initialStr.length()];
        for (int i = 0; i < initialStr.length(); i++) {
            encoded[i] = encoder.encodeChar(initialStr.charAt(i));
        }

        char[] decoded = new char[encoded.length];
        for (int i = 0; i < encoded.length; i++) {
            decoded[i] = decoder.decodeChar(encoded[i]);
        }

        String resultStr = new String(decoded);

        // THEN
        assertEquals(initialStr, resultStr);
    }
}
