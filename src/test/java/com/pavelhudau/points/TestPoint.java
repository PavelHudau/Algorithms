package com.pavelhudau.points;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.stream.Stream;

public class TestPoint {
    @ParameterizedTest
    @CsvSource({
            "2, 2, 1, 1, 1",
            "0, 2, 0, 1, 1",
            "1, 2, 0, 2, 1",
            "0, 1, 0, 2, -1",
            "0, 2, 1, 2, -1",
            "2, 2, 2, 2, 0",
    })
    void testCompareTo(int thisX, int thisY, int thatX, int thatY, int expected) {
        Point thisPoint = new Point(thisX, thisY);
        Point thatPoint = new Point(thatX, thatY);
        assertEquals(expected, thisPoint.compareTo(thatPoint));
    }

    @ParameterizedTest
    @MethodSource("testSlopeToSource")
    void testSlopeTo(int thisX, int thisY, int thatX, int thatY, double expectedSlope) {
        Point thisPoint = new Point(thisX, thisY);
        Point thatPoint = new Point(thatX, thatY);
        assertEquals(expectedSlope, thisPoint.slopeTo(thatPoint));
    }

    private static Stream<Arguments> testSlopeToSource() {
        return Stream.of(
                Arguments.of(2, 2, 1, 1, 1.0),
                Arguments.of(2, 2, 0, 0, 1.0),
                Arguments.of(1, 1, 2, 2, 1.0),
                Arguments.of(0, 0, 2, 2, 1.0),
                Arguments.of(2, 3, 0, 0, 1.5),
                Arguments.of(32767, 0, 1, 1, -1.0 / 32766.0),
                Arguments.of(0, 32767, 1, 1, -32766.0),
                Arguments.of(1, 1, 32767, 0, -1.0 / 32766.0),
                Arguments.of(1, 1, 0, 32767, -32766.0),
                // Same point
                Arguments.of(0, 0, 0, 0, Double.NEGATIVE_INFINITY),
                // Vertical line
                Arguments.of(1, 2, 1, 1, Double.POSITIVE_INFINITY),
                // Horizontal line
                Arguments.of(2, 2, 1, 2, 0.0)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "2, 2, 1, 1, 0",
            "1, 2, 1, 1, 1",
            "1, 1, 1, 2, -1",
            "0, 2, 0, 1, 0",
            "2, 0, 1, 0, 0",
    })
    void testSlopeOrder(int thisX, int thisY, int thatX, int thatY, int expected) {
        Point zeroPoint = new Point(0, 0);
        Comparator<Point> comparator = zeroPoint.slopeOrder();
        assertEquals(expected, comparator.compare(new Point(thisX, thisY), new Point(thatX, thatY)));
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 1",
            "1, -1",
            "-1, -1",
            "32768, 1",
            "1, 32768",
            "32768, 32768",
    })
    void testWhenInvalidXOrYThenExceptionIsThrown(int x, int y) {
        assertThrows(IllegalArgumentException.class, () -> new Point(x, y));
    }
}
