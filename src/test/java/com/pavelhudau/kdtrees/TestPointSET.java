package com.pavelhudau.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import org.junit.jupiter.api.Test;

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
    void testDraw() {
        // GIVEN
        PointSET ps = new PointSET();
        // WHEN
        ps.insert(new Point2D(0.1, 0.2));
        // THEN
        assertDoesNotThrow(() -> {
            ps.draw();
        });
    }
}
