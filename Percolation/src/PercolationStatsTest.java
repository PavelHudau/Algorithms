import org.junit.jupiter.api.Test;

public class PercolationStatsTest {
    @Test
    void open_small() {
        PercolationStats percolationStats = new PercolationStats(5, 10);
        assert percolationStats.mean() > 0;
        assert percolationStats.stddev() > 0;
        assert percolationStats.confidenceHi() > 0;
        assert percolationStats.confidenceLo() > 0;
        assert percolationStats.confidenceLo() < percolationStats.confidenceHi();
    }

    @Test
    void open_large() {
        PercolationStats percolationStats = new PercolationStats(40, 1000);
        assert percolationStats.mean() > 0;
        assert percolationStats.stddev() > 0;
        assert percolationStats.confidenceHi() > 0;
        assert percolationStats.confidenceLo() > 0;
        assert percolationStats.confidenceLo() < percolationStats.confidenceHi();
    }
}
