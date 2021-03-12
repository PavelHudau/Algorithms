package com.pavelhudau.seamcarving;

import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private final static double BORDER_ENERGY = 1000;
    private final Picture picture;
    private double[][] energies;
    private int[] horizontalSeam;
    private int[] verticalSeam;

    /**
     * Create a seam carver object based on the given picture.
     * Original picture will not be mutated by SeamCarver.
     *
     * @param picture a picture.
     */
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("picture can not be null.");
        }

        this.picture = new Picture(picture);
    }

    /**
     * Current picture.
     *
     * @return current picture.
     */
    public Picture picture() {
        return this.picture;
    }

    /**
     * Width of current picture.
     *
     * @return width of current picture.
     */
    public int width() {
        return this.picture.width();
    }

    /**
     * Height of current picture.
     *
     * @return height of current picture.
     */
    public int height() {
        return this.picture.height();
    }

    /**
     * Energy of pixel at column x and row y.
     *
     * @param x column X.
     * @param y column Y.
     * @return Energy of pixel at column x and row y.
     */
    public double energy(int x, int y) {
        int maxY = this.height() - 1;
        int maxX = this.width() - 1;

        if (x < 0 || x > maxX || y < 0 || y > maxY) {
            throw new IllegalArgumentException("x == " + x + ", y == " + y + " and are outside of the boundary");
        }

        if (x == 0 || y == 0 || x == maxX || y == maxY) {
            return BORDER_ENERGY;
        }

        double deltaX = getDeltaSquared(this.picture.get(x - 1, y), this.picture.get(x + 1, y));
        double deltaY = getDeltaSquared(this.picture.get(x, y - 1), this.picture.get(x, y + 1));
        return Math.sqrt(deltaX + deltaY);
    }

    /**
     * Sequence of indices for horizontal seam.
     *
     * @return sequence of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {
        if (this.horizontalSeam == null) {
            TopologicalShortestHorizontalPath pathFinder = new TopologicalShortestHorizontalPath(this.getEnergies());
            this.horizontalSeam = pathFinder.findShortestPath();
        }

        return this.horizontalSeam;
    }

    /**
     * Sequence of indices for vertical seam.
     *
     * @return sequence of indices for vertical seam.
     */
    public int[] findVerticalSeam() {
        if (this.verticalSeam == null) {
            TopologicalShortestVerticalPath pathFinder = new TopologicalShortestVerticalPath(this.getEnergies());
            this.verticalSeam = pathFinder.findShortestPath();
        }

        return this.verticalSeam;
    }

    /**
     * Remove horizontal seam from current picture
     *
     * @param seam Seam to remove.
     */
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam can not be null.");
        }
        if (seam.length != this.height()) {
            throw new IllegalArgumentException("Invalid seam size");
        }
        if (this.height() < 2) {
            throw new IllegalArgumentException("Picture is too small to remove seam.");
        }

        int[] horizontalSeam = this.findHorizontalSeam();
        // TODO: remove seam;
        this.reset();
    }

    /**
     * Remove vertical seam from current picture
     *
     * @param seam Seam to remoe.
     */
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam can not be null.");
        }
        if (seam.length != this.width()) {
            throw new IllegalArgumentException("Invalid seam size");
        }
        if (this.width() < 2) {
            throw new IllegalArgumentException("Picture is too small to remove seam.");
        }
        int[] verticalSeam = this.findVerticalSeam();
        // TODO: remove seam;
        this.reset();
    }

    private double[][] getEnergies() {
        if (this.energies == null) {
            this.energies = new double[this.width()][this.height()];
            for (int x = 0; x < this.energies.length; x++) {
                for (int y = 0; y < this.energies[x].length; y++) {
                    this.energies[x][y] = this.energy(x, y);
                }
            }
        }

        return this.energies;
    }

    private static double getDeltaSquared(Color pixelA, Color pixelB) {
        return Math.pow(pixelA.getRed() - pixelB.getRed(), 2) +
                Math.pow(pixelA.getGreen() - pixelB.getGreen(), 2) +
                Math.pow(pixelA.getBlue() - pixelB.getBlue(), 2);
    }

    private void reset() {
        this.energies = null;
        this.horizontalSeam = null;
        this.verticalSeam = null;
    }
}
