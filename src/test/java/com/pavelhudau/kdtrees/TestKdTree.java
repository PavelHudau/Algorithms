package com.pavelhudau.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestKdTree {
    @Test
    void testIsEmptyWhenEmpty() {
        KdTree kd = new KdTree();
        assertTrue(kd.isEmpty());
    }

    @Test
    void testIsEmptyWhenNotEmpty() {
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.1, 0.2));
        assertFalse(kd.isEmpty());
    }

    @Test
    void testSizeWhenEmpty() {
        KdTree kd = new KdTree();
        assertEquals(0, kd.size());
    }

    @Test
    void testSizeWhenNotEmpty() {
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.1, 0.2));
        kd.insert(new Point2D(0.2, 0.1));
        assertEquals(2, kd.size());
    }

    @Test
    void testInsertUniquePoints() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        kd.insert(new Point2D(0.1, 0.2));
        kd.insert(new Point2D(0.2, 0.1));
        //THEN
        assertEquals(2, kd.size());
        assertTrue(kd.contains(new Point2D(0.1, 0.2)));
        assertTrue(kd.contains(new Point2D(0.2, 0.1)));
        assertFalse(kd.contains(new Point2D(0.3, 0.3)));
    }

    @Test
    void testInsertSamePoints() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        kd.insert(new Point2D(0.1, 0.2));
        kd.insert(new Point2D(0.1, 0.2));
        kd.insert(new Point2D(0.1, 0.2));
        //THEN
        assertEquals(1, kd.size());
        assertTrue(kd.contains(new Point2D(0.1, 0.2)));
        assertFalse(kd.contains(new Point2D(0.2, 0.1)));
        assertFalse(kd.contains(new Point2D(0.3, 0.3)));
    }

    @Test
    void testInsertNull() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> kd.insert(null));
    }

    @Test
    void testContainsWhenEmpty() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        //THEN
        assertFalse(kd.contains(new Point2D(0.1, 0.2)));
    }

    @Test
    void testContainsWhenNotEmpty() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        kd.insert(new Point2D(0.1, 0.2));
        kd.insert(new Point2D(0.1, 0.2));
        kd.insert(new Point2D(0.2, 0.1));
        //THEN
        assertTrue(kd.contains(new Point2D(0.1, 0.2)));
        assertTrue(kd.contains(new Point2D(0.2, 0.1)));
        assertFalse(kd.contains(new Point2D(0.3, 0.3)));
    }

    @Test
    void testContainsNull() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> kd.contains(null));
    }

    @Test
    void testRange() {
        // GIVEN
        KdTree kd = new KdTree();
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
            kd.insert(p);
        }
        for (Point2D p : pointsOutRange) {
            kd.insert(p);
        }
        //THEN
        Iterable<Point2D> range = kd.range(new RectHV(0.1, 0.1, 0.7, 0.7));
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
        KdTree kd = new KdTree();
        // WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> kd.range(null));
    }

    @Test
    void testNearestWithEmptySet() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        //THEN
        assertNull(kd.nearest(new Point2D(0.3, 0.3)));
        assertNull(kd.nearest(new Point2D(0.1, 0.1)));
    }

    @Test
    void testNearestWithOnlyOnePoint() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        kd.insert(new Point2D(0.1, 0.1));
        //THEN
        assertEquals(new Point2D(0.1, 0.1), kd.nearest(new Point2D(0.3, 0.3)));
        assertEquals(new Point2D(0.1, 0.1), kd.nearest(new Point2D(0.1, 0.1)));
    }

    @Test
    void testNearestWithOnlyTwoPoints() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        kd.insert(new Point2D(0.1, 0.1));
        kd.insert(new Point2D(0.3, 0.3));
        //THEN
        assertEquals(new Point2D(0.3, 0.3), kd.nearest(new Point2D(0.3, 0.3)));
        assertEquals(new Point2D(0.1, 0.1), kd.nearest(new Point2D(0.1, 0.1)));
        assertEquals(new Point2D(0.3, 0.3), kd.nearest(new Point2D(0.33, 0.33)));
        assertEquals(new Point2D(0.1, 0.1), kd.nearest(new Point2D(0.099, 0.099)));
    }

    @Test
    void testNearestWithSingleNearest() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        kd.insert(new Point2D(0.1, 0.1));
        kd.insert(new Point2D(0.2, 0.2));
        kd.insert(new Point2D(0.4, 0.4));
        kd.insert(new Point2D(0.5, 0.5));
        kd.insert(new Point2D(0.1, 0.5));
        kd.insert(new Point2D(0.2, 0.4));
        kd.insert(new Point2D(0.4, 0.2));
        kd.insert(new Point2D(0.5, 0.1));
        kd.insert(new Point2D(0.3, 0.4));
        //THEN
        assertEquals(new Point2D(0.3, 0.4), kd.nearest(new Point2D(0.3, 0.3)));
    }

    @Test
    void testNearestWithMultipleNearest() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        kd.insert(new Point2D(0.1, 0.1));
        kd.insert(new Point2D(0.2, 0.2));
        kd.insert(new Point2D(0.4, 0.4));
        kd.insert(new Point2D(0.5, 0.5));
        kd.insert(new Point2D(0.1, 0.5));
        kd.insert(new Point2D(0.2, 0.4));
        kd.insert(new Point2D(0.4, 0.2));
        kd.insert(new Point2D(0.5, 0.1));
        Set<Point2D> nearest = new TreeSet<>();
        nearest.add(new Point2D(0.2, 0.3));
        nearest.add(new Point2D(0.3, 0.4));
        nearest.add(new Point2D(0.3, 0.2));
        nearest.add(new Point2D(0.4, 0.3));
        for (Point2D p : nearest) {
            kd.insert(p);
        }
        //THEN
        assertTrue(nearest.contains(kd.nearest(new Point2D(0.3, 0.3))));
    }

    @Test
    void testNearestWhenPointNull() {
        // GIVEN
        KdTree kd = new KdTree();
        // WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> kd.nearest(null));
    }
}
