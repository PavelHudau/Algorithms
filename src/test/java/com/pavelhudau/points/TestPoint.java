package com.pavelhudau.points;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.stream.Stream;

public class TestPoint {
    @ParameterizedTest
    @CsvSource({
            "2, 2, 1, 1, 1",
            "2, 0, 1, 0, 1",
            "2, 1, 2, 0, 1",
            "1, 0, 2, 0, -1",
            "2, 0, 2, 1, -1",
            "2, 2, 2, 2, 0",
    })
    void testCompareTo(int thisY, int thisX, int thatY, int thatX, int expected) {
        Point thisPoint = new Point(thisX, thisY);
        Point thatPoint = new Point(thatX, thatY);
        assert thisPoint.compareTo(thatPoint) == expected;
    }

    @ParameterizedTest
    @MethodSource("testSlopeToSource")
    void testSlopeTo(int thisY, int thisX, int thatY, int thatX, double expectedSlope) {
        Point thisPoint = new Point(thisX, thisY);
        Point thatPoint = new Point(thatX, thatY);
        assert thisPoint.slopeTo(thatPoint) == expectedSlope;
    }

    private static Stream<Arguments> testSlopeToSource() {
        return Stream.of(
                Arguments.of(2, 2, 1, 1, 1.0),
                Arguments.of(2, 2, 0, 0, 1.0),
                Arguments.of(1, 1, 2, 2, 1.0),
                Arguments.of(0, 0, 2, 2, 1.0),
                Arguments.of(3, 2, 0, 0, 1.5),
                Arguments.of(-2, -2, -1, -1, 1.0),
                Arguments.of(-2, -2, 0, 0, 1.0),
                Arguments.of(-1, -1, -2, -2, 1.0),
                Arguments.of(0, 0, -2, -2, 1.0),
                Arguments.of(-2, 2, -1, 1, -1.0),
                Arguments.of(-2, 2, 0, 0, -1.0),
                Arguments.of(-1, 1, -2, 2, -1.0),
                Arguments.of(0, 0, -2, 2, -1.0),
                Arguments.of(2, -2, 1, -1, -1.0),
                Arguments.of(2, -2, 0, 0, -1.0),
                Arguments.of(1, -1, 2, -2, -1.0),
                Arguments.of(0, 0, 2, -2, -1.0),
                // Same point
                Arguments.of(0, 0, 0, 0, Double.NEGATIVE_INFINITY),
                // Vertical line
                Arguments.of(2, 1, 1, 1, Double.POSITIVE_INFINITY),
                // Horizontal line
                Arguments.of(2, 2, 2, 1, 0.0)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "2, 2, 1, 1, 0",
            "2, 1, 1, 1, 1",
            "1, 1, 2, 1, -1",
            "2, 0, 1, 0, 0",
            "0, 2, 0, 1, 0",
    })
    void testSlopeOrder(int thisY, int thisX, int thatY, int thatX, int expected) {
        Point zeroPoint = new Point(0, 0);
        Comparator<Point> comparator =  zeroPoint.slopeOrder();
        assert  comparator.compare(new Point(thisX, thisY), new Point(thatX, thatY)) == expected;
    }
}
