package com.pavelhudau.percolation;

import org.junit.jupiter.api.Test;

public class TestPercolationStats {
    @Test
    void testOpenSmall() {
        PercolationStats percolationStats = new PercolationStats(5, 10);
        assert percolationStats.mean() > 0;
        assert percolationStats.stddev() > 0;
        assert percolationStats.confidenceHi() > 0;
        assert percolationStats.confidenceLo() > 0;
        assert percolationStats.confidenceLo() < percolationStats.confidenceHi();
    }

    @Test
    void testOpenLarge() {
        PercolationStats percolationStats = new PercolationStats(40, 1000);
        assert percolationStats.mean() > 0;
        assert percolationStats.stddev() > 0;
        assert percolationStats.confidenceHi() > 0;
        assert percolationStats.confidenceLo() > 0;
        assert percolationStats.confidenceLo() < percolationStats.confidenceHi();
    }
}
