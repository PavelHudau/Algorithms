package com.pavelhudau.points;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        BruteCollinearPoints alg = new BruteCollinearPoints(points);
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
        assertEquals(0, alg.segments().length);
        assertEquals(0, alg.numberOfSegments());
    }
}
