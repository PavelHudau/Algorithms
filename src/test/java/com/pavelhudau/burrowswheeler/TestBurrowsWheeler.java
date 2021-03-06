package com.pavelhudau.burrowswheeler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBurrowsWheeler {
    @ParameterizedTest
    @CsvSource({
            "ABRACADABRA!, ARD!RCAAAABB, 3",
            "AAA, AAA, 0",
            "A, A, 0"
    })
    void testTransform(String inputStr, String expectedTransformedStr, int expectedOriginalStrIndex) {
        // GIVEN
        StdInOutHelper inOutTransform = new StdInOutHelper(inputStr);

        // WHEN
        BurrowsWheeler.main(new String[]{"-"});
        byte[] transformed = inOutTransform.readOutputAndClose();

        // THEN
        BytesReader transformedReader = new BytesReader(transformed);
        int originalStrIndex = transformedReader.readInt();
        String transformedStr = transformedReader.readString();
        transformedReader.close();

        assertEquals(expectedOriginalStrIndex, originalStrIndex);
        assertEquals(expectedTransformedStr, transformedStr);
    }

    @ParameterizedTest
    @CsvSource({
            "ARD!RCAAAABB, 3, ABRACADABRA!",
            "AAA, 0, AAA",
            "A, 0, A"
    })
    void testInverseTransform(String transformedStr, int originalStrIndex, String expectedInverseTransformedStr) {
        // GIVEN
        BytesWriter transformedWriter = new BytesWriter();
        transformedWriter.write(originalStrIndex);
        transformedWriter.write(transformedStr);
        byte[] transformed = transformedWriter.readBytesAndClose();

        // WHEN
        StdInOutHelper inOutInverseTransform = new StdInOutHelper(transformed);
        BurrowsWheeler.main(new String[]{"+"});
        byte[] inverseTransformed = inOutInverseTransform.readOutputAndClose();
        String inverseTransformedStr = new String(inverseTransformed);

        // THEN
        assertEquals(expectedInverseTransformedStr, inverseTransformedStr);
    }

    @Test
    void testTransformAndInverseTransformOfAllChars() {
        // GIVEN
        BytesWriter originalBytesWriter = new BytesWriter();
        for (char c = 0; c <= 255; c++) {
            originalBytesWriter.write(c);
        }

        byte[] originalBytes = originalBytesWriter.readBytesAndClose();

        // WHEN
        StdInOutHelper inOutInTransform = new StdInOutHelper(originalBytes);
        BurrowsWheeler.main(new String[]{"-"});
        byte[] transformedBytes = inOutInTransform.readOutputAndClose();

        StdInOutHelper inOutInverseTransform = new StdInOutHelper(transformedBytes);
        BurrowsWheeler.main(new String[]{"+"});
        byte[] inverseTransformed = inOutInverseTransform.readOutputAndClose();

        // THEN
        assertArrayEquals(originalBytes, inverseTransformed);
    }
}
