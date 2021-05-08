package com.pavelhudau.burrowswheeler;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestMoveToFront {

    @ParameterizedTest
    @ValueSource(strings = {
            "ABRACADABRA!",
            "AAA",
            "A"
    })
    void testEncodeAndDecode(String initialStr) {
        // GIVEN
        StdInOutHelper inOutHelperEncode = new StdInOutHelper(initialStr);

        // WHEN
        MoveToFront.main(new String[]{"-"}); // Encode
        byte[] encoded = inOutHelperEncode.readOutputAndClose();

        StdInOutHelper inOutHelperDecode = new StdInOutHelper(encoded);
        MoveToFront.main(new String[]{"+"}); // Decode
        byte[] decoded = inOutHelperDecode.readOutputAndClose();
        String decodedStr = new String(decoded);

        // THEN
        assertEquals(initialStr, decodedStr);
    }
}
