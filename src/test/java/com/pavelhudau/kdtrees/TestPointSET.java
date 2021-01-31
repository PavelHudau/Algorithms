package com.pavelhudau.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class TestPointSET {
    @Test
    void testIsEmptyWhenEmpty() {
        PointSET ps = new PointSET();
        assertTrue(ps.isEmpty());
    }

    @Test
    void testIsEmptyWhenNotEmpty() {
        PointSET ps = new PointSET();
        ps.insert(new Point2D(0.1, 0.2));
        assertFalse(ps.isEmpty());
    }

    @Test
    void testSizeWhenEmpty() {
        PointSET ps = new PointSET();
        assertEquals(0, ps.size());
    }

    @Test
    void testSizeWhenNotEmpty() {
        PointSET ps = new PointSET();
        ps.insert(new Point2D(0.1, 0.2));
        ps.insert(new Point2D(0.2, 0.1));
        assertEquals(2, ps.size());
    }

    @Test
    void testInsertUniquePoints() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        ps.insert(new Point2D(0.1, 0.2));
        ps.insert(new Point2D(0.2, 0.1));
        //THEN
        assertEquals(2, ps.size());
        assertTrue(ps.contains(new Point2D(0.1, 0.2)));
        assertTrue(ps.contains(new Point2D(0.2, 0.1)));
        assertFalse(ps.contains(new Point2D(0.3, 0.3)));
    }

    @Test
    void testInsertSamePoints() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        ps.insert(new Point2D(0.1, 0.2));
        ps.insert(new Point2D(0.1, 0.2));
        ps.insert(new Point2D(0.1, 0.2));
        //THEN
        assertEquals(1, ps.size());
        assertTrue(ps.contains(new Point2D(0.1, 0.2)));
        assertFalse(ps.contains(new Point2D(0.2, 0.1)));
        assertFalse(ps.contains(new Point2D(0.3, 0.3)));
    }

    @Test
    void testInsertNull() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> ps.insert(null));
    }

    @Test
    void testContainsWhenEmpty() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        //THEN
        assertFalse(ps.contains(new Point2D(0.1, 0.2)));
    }

    @Test
    void testContainsWhenNotEmpty() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        ps.insert(new Point2D(0.1, 0.2));
        ps.insert(new Point2D(0.1, 0.2));
        ps.insert(new Point2D(0.2, 0.1));
        //THEN
        assertTrue(ps.contains(new Point2D(0.1, 0.2)));
        assertTrue(ps.contains(new Point2D(0.2, 0.1)));
        assertFalse(ps.contains(new Point2D(0.3, 0.3)));
    }

    @Test
    void testContainsNull() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> ps.contains(null));
    }

    @Test
    void testRange() {
        // GIVEN
        PointSET ps = new PointSET();
        Set<Point2D> pointsInRange = new TreeSet<>();
        pointsInRange.add(new Point2D(0.1, 0.1));
        pointsInRange.add(new Point2D(0.7, 0.7));
        pointsInRange.add(new Point2D(0.5, 0.5));
        pointsInRange.add(new Point2D(0.1, 0.7));
        pointsInRange.add(new Point2D(0.7, 0.1));
        Set<Point2D> pointsOutRange = new TreeSet<>();
        pointsOutRange.add(new Point2D(0, 0));
        pointsOutRange.add(new Point2D(0.9, 0.9));
        pointsOutRange.add(new Point2D(0.05, 0.1));
        pointsOutRange.add(new Point2D(0.1, 0.05));
        pointsOutRange.add(new Point2D(0.05, 0.7));
        pointsOutRange.add(new Point2D(0.7, 0.05));
        pointsOutRange.add(new Point2D(0.75, 0.1));
        pointsOutRange.add(new Point2D(0.1, 0.75));
        pointsOutRange.add(new Point2D(0.75, 0.7));
        pointsOutRange.add(new Point2D(0.7, 0.75));
        // WHEN
        for (Point2D p : pointsInRange) {
            ps.insert(p);
        }
        for (Point2D p : pointsOutRange) {
            ps.insert(p);
        }
        //THEN
        Iterable<Point2D> range = ps.range(new RectHV(0.1, 0.1, 0.7, 0.7));
        int pointsInRangeCnt = 0;
        for (Point2D p : range) {
            assertTrue(pointsInRange.contains(p));
            assertFalse(pointsOutRange.contains(p));
            pointsInRangeCnt++;
        }
        assertEquals(5, pointsInRangeCnt);
    }

    @Test
    void testRangeWhenRectangleNull() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> ps.range(null));
    }

    @Test
    void testNearestWithEmptySet() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        //THEN
        assertNull(ps.nearest(new Point2D(0.3, 0.3)));
        assertNull(ps.nearest(new Point2D(0.1, 0.1)));
    }

    @Test
    void testNearestWithOnlyOnePoint() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        ps.insert(new Point2D(0.1, 0.1));
        //THEN
        assertEquals(new Point2D(0.1, 0.1), ps.nearest(new Point2D(0.3, 0.3)));
        assertEquals(new Point2D(0.1, 0.1), ps.nearest(new Point2D(0.1, 0.1)));
    }

    @Test
    void testNearestWithOnlyTwoPoints() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        ps.insert(new Point2D(0.1, 0.1));
        ps.insert(new Point2D(0.3, 0.3));
        //THEN
        assertEquals(new Point2D(0.3, 0.3), ps.nearest(new Point2D(0.3, 0.3)));
        assertEquals(new Point2D(0.1, 0.1), ps.nearest(new Point2D(0.1, 0.1)));
        assertEquals(new Point2D(0.3, 0.3), ps.nearest(new Point2D(0.33, 0.33)));
        assertEquals(new Point2D(0.1, 0.1), ps.nearest(new Point2D(0.099, 0.099)));
    }

    @Test
    void testNearestWithSingleNearest() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        ps.insert(new Point2D(0.1, 0.1));
        ps.insert(new Point2D(0.2, 0.2));
        ps.insert(new Point2D(0.4, 0.4));
        ps.insert(new Point2D(0.5, 0.5));
        ps.insert(new Point2D(0.1, 0.5));
        ps.insert(new Point2D(0.2, 0.4));
        ps.insert(new Point2D(0.4, 0.2));
        ps.insert(new Point2D(0.5, 0.1));
        ps.insert(new Point2D(0.3, 0.4));
        //THEN
        assertEquals(new Point2D(0.3, 0.4), ps.nearest(new Point2D(0.3, 0.3)));
    }

    @Test
    void testNearestWithMultipleNearest() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        ps.insert(new Point2D(0.1, 0.1));
        ps.insert(new Point2D(0.2, 0.2));
        ps.insert(new Point2D(0.4, 0.4));
        ps.insert(new Point2D(0.5, 0.5));
        ps.insert(new Point2D(0.1, 0.5));
        ps.insert(new Point2D(0.2, 0.4));
        ps.insert(new Point2D(0.4, 0.2));
        ps.insert(new Point2D(0.5, 0.1));
        Set<Point2D> nearest = new TreeSet<>();
        nearest.add(new Point2D(0.2, 0.3));
        nearest.add(new Point2D(0.3, 0.4));
        nearest.add(new Point2D(0.3, 0.2));
        nearest.add(new Point2D(0.4, 0.3));
        for (Point2D p : nearest) {
            ps.insert(p);
        }
        //THEN
        assertTrue(nearest.contains(ps.nearest(new Point2D(0.3, 0.3))));
    }

    @Test
    void testNearestWhenPointNull() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> ps.nearest(null));
    }
}
