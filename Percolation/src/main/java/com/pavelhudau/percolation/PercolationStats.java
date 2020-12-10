package com.pavelhudau.percolation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PercolationStats {
    class RowCol {
        int row;
        int col;

        RowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private Random random = new Random();
    private ArrayList<Double> percolationThresholds;
    private double percolationThresholdsMean = 0;
    private double percolationThresholdsStd = 0;
    private int trials = 0;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("trials must be > 0");
        }

        this.percolationThresholds = new ArrayList<>(n);
        this.trials = trials;
        double totalNumberOfSlots = Math.pow(n, 2);
        double percolationThresholdsSum = 0;

        ArrayList<RowCol> rowsCols = new ArrayList<>(n * n);
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                rowsCols.add(new RowCol(row, col));
            }
        }

        while (trials > 0) {
            Collections.shuffle(rowsCols, this.random);
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
            this.percolationThresholds.add(percolationThreshold);
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

    // test client (see below)
    public static void main(String[] args) {
        Integer n = Integer.parseInt(args[0]);
        Integer trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean =                    " + percolationStats.mean());
        System.out.println("stddev =                  " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" +
                percolationStats.confidenceLo() +
                ", " +
                percolationStats.confidenceHi());
    }
}
