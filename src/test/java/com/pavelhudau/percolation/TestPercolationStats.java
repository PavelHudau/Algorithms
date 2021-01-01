package com.pavelhudau.percolation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPercolationStats {
    @Test
    void testOpenSmall() {
        PercolationStats percolationStats = new PercolationStats(5, 10);
        assertTrue(percolationStats.mean() > 0);
        assertTrue(percolationStats.stddev() > 0);
        assertTrue(percolationStats.confidenceHi() > 0);
        assertTrue(percolationStats.confidenceLo() > 0);
        assertTrue(percolationStats.confidenceLo() < percolationStats.confidenceHi());
    }

    @Test
    void testOpenLarge() {
        PercolationStats percolationStats = new PercolationStats(40, 1000);
        assertTrue(percolationStats.mean() > 0);
        assertTrue(percolationStats.stddev() > 0);
        assertTrue(percolationStats.confidenceHi() > 0);
        assertTrue(percolationStats.confidenceLo() > 0);
        assertTrue(percolationStats.confidenceLo() < percolationStats.confidenceHi());
    }
}
