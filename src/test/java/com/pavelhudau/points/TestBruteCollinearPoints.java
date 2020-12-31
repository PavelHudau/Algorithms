package com.pavelhudau.points;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestBruteCollinearPoints {
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
        BruteCollinearPoints alg = new BruteCollinearPoints(points);
        assert alg.numberOfSegments() == 1;
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
        BruteCollinearPoints alg = new BruteCollinearPoints(points);
        assert alg.numberOfSegments() == 2;
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
        assert alg.segments().length == 0;
        assert alg.numberOfSegments() == 0;
    }

    @Test
    void testWhenNullPointsThenThrowsException() {
        Point[] points = new Point[]{
                new Point(5, 6),
                new Point(6, 6),
                new Point(7, 6),
                null,
                new Point(5, 5),
                new Point(8, 6),
                new Point(5, 10)
        };
        assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(points));
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
        assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(points));
    }

    @Test
    void testWhenPointIsNullThenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(null));
    }

    @Test
    void testWhenZeroPointsReturnsNoSegments() {
        BruteCollinearPoints alg = new BruteCollinearPoints(new Point[0]);
        assert alg.segments().length == 0;
        assert alg.numberOfSegments() == 0;
    }
}
