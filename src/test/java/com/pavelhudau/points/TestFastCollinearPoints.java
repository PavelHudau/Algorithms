package com.pavelhudau.points;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestFastCollinearPoints {

    @Test
    void testNullPoints() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FastCollinearPoints(null);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 7})
    void testOneOfThePointsIsNullPoints(int nullIdx) {
        Point[] points = new Point[]{
                new Point(5, 6),
                new Point(6, 6),
                new Point(7, 6),
                new Point(1, 4),
                new Point(5, 5),
                new Point(8, 6),
                new Point(5, 10),
                new Point(5, 6)
        };
        points[nullIdx] = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new FastCollinearPoints(points);
        });
    }
}
