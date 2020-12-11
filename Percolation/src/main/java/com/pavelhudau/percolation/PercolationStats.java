package com.pavelhudau.percolation;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static class RowCol {
        int row;
        int col;

        private RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private final double[] percolationThresholds;
    private final double percolationThresholdsMean;
    private final double percolationThresholdsStd;
    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("trials must be > 0");
        }

        this.percolationThresholds = new double[trials];
        this.trials = trials;
        double totalNumberOfSlots = Math.pow(n, 2);
        double percolationThresholdsSum = 0;
        RowCol[] rowsCols = new RowCol[n * n];
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                rowsCols[(row - 1) * n + col - 1] = new RowCol(row, col);
            }
        }

        while (trials > 0) {
            this.shuffleRowCols(rowsCols);
            Percolation percolation = new Percolation(n);
            boolean percolates = false;
            for (RowCol rowCol : rowsCols) {
                percolation.open(rowCol.row, rowCol.col);
                percolates = percolation.percolates();
                if (percolates) {
                    break;
                }
            }

            if (!percolates) {
                throw new IllegalStateException("Exhausted all sites but still didn't achieve percolation. Must be an error in the algorithm.");
            }

            double percolationThreshold = percolation.numberOfOpenSites() / totalNumberOfSlots;
            this.percolationThresholds[trials - 1] = percolationThreshold;
            percolationThresholdsSum += percolationThreshold;
            trials--;
        }

        this.percolationThresholdsMean = percolationThresholdsSum / this.trials;
        this.percolationThresholdsStd = this.calculateStd();
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.percolationThresholdsMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.percolationThresholdsStd;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.percolationThresholdsMean - (1.96 * this.percolationThresholdsStd / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.percolationThresholdsMean + (1.96 * this.percolationThresholdsStd / Math.sqrt(this.trials));
    }

    private double calculateStd() {
        double sum = 0;
        for (double threshold : this.percolationThresholds) {
            sum += Math.pow(threshold - this.percolationThresholdsMean, 2);
        }
        return Math.sqrt(sum / (this.trials - 1));
    }

    private void shuffleRowCols(RowCol[] rowCols) {
        for (int i = 0; i < rowCols.length; i++) {
            int randPosition = StdRandom.uniform(rowCols.length);
            RowCol ith = rowCols[i];
            rowCols[i] = rowCols[randPosition];
            rowCols[randPosition] = ith;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean =                    " + percolationStats.mean());
        System.out.println("stddev =                  " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" +
                percolationStats.confidenceLo() +
                ", " +
                percolationStats.confidenceHi());
    }
}
