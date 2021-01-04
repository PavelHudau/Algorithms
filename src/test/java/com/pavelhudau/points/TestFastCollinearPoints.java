package com.pavelhudau.points;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestFastCollinearPoints {
    @Test
    void testOneSegment() {
        Point[] points = new Point[]{
                new Point(5, 6),
                new Point(6, 6),
                new Point(7, 6),
                new Point(1, 4),
                new Point(5, 5),
                new Point(8, 6),
                new Point(5, 10)
        };
        FastCollinearPoints alg = new FastCollinearPoints(points);
        assertEquals(1, alg.numberOfSegments());
    }

    @Test
    void testTwoSegments() {
        Point[] points = new Point[]{
                new Point(5, 6),
                new Point(6, 6),
                new Point(7, 6),
                new Point(5, 4),
                new Point(5, 5),
                new Point(8, 6),
                new Point(5, 10)
        };
        FastCollinearPoints alg = new FastCollinearPoints(points);
        assertEquals(2, alg.numberOfSegments());
    }

    @Test
    void testZeroSegments() {
        Point[] points = new Point[]{
                new Point(5, 6),
                new Point(6, 6),
                new Point(7, 6),
                new Point(5, 4),
                new Point(5, 5)
        };
        BruteCollinearPoints alg = new BruteCollinearPoints(points);
        assertEquals(0, alg.segments().length);
        assertEquals(0, alg.numberOfSegments());
    }

    @Test
    void testWhenNullPointsThenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FastCollinearPoints(null);
        });
    }

    @Test
    void testWhenDuplicatePointsThenThrowsException() {
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
        assertThrows(IllegalArgumentException.class, () -> new FastCollinearPoints(points));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 7})
    void testOneOfThePointsIsNullThenThrowsException(int nullIdx) {
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

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testWhenSmallArrayAndOneOfThePointsIsNullThenThrowsException(int size) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(1, i);
        }
        points[0] = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new FastCollinearPoints(points);
        });
    }

    @Test
    void testWhenZeroPointsReturnsNoSegments() {
        FastCollinearPoints alg = new FastCollinearPoints(new Point[0]);
        assertEquals(0, alg.segments().length);
        assertEquals(0, alg.numberOfSegments());
    }
}
