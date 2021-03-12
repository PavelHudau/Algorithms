package com.pavelhudau.seamcarving;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private static final double BORDER_ENERGY = 1000;
    private Picture picture;
    private double[][] energies;

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

        double deltaX = getDeltaSquared(this.picture.getRGB(x - 1, y), this.picture.getRGB(x + 1, y));
        double deltaY = getDeltaSquared(this.picture.getRGB(x, y - 1), this.picture.getRGB(x, y + 1));
        return Math.sqrt(deltaX + deltaY);
    }

    /**
     * Sequence of indices for horizontal seam.
     *
     * @return sequence of indices for horizontal seam.
     */
    public int[] findHorizontalSeam() {
        TopologicalShortestHorizontalPath pathFinder = new TopologicalShortestHorizontalPath(this.getEnergies());
        return pathFinder.findShortestPath();
    }

    /**
     * Sequence of indices for vertical seam.
     *
     * @return sequence of indices for vertical seam.
     */
    public int[] findVerticalSeam() {
        TopologicalShortestVerticalPath pathFinder = new TopologicalShortestVerticalPath(this.getEnergies());
        return pathFinder.findShortestPath();
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
        if (seam.length != this.width()) {
            throw new IllegalArgumentException("Invalid seam size");
        }
        if (this.height() < 2) {
            throw new IllegalArgumentException("Picture is too small to remove seam.");
        }

        Picture newPicture = new Picture(this.width(), this.height() - 1);
        for (int x = 0; x < this.width(); x++) {
            for (int y = 0; y < seam[x]; y++) {
                newPicture.setRGB(x, y, this.picture.getRGB(x, y));
            }
            for (int y = seam[x] + 1; y < this.height(); y++) {
                newPicture.setRGB(x, y - 1, this.picture.getRGB(x, y));
            }
        }

        this.setNewPicture(newPicture);
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
        if (seam.length != this.height()) {
            throw new IllegalArgumentException("Invalid seam size");
        }
        if (this.width() < 2) {
            throw new IllegalArgumentException("Picture is too small to remove seam.");
        }

        Picture newPicture = new Picture(this.width() - 1, this.height());
        for (int y = 0; y < this.height(); y++) {
            for (int x = 0; x < seam[y]; x++) {
                newPicture.setRGB(x, y, this.picture.getRGB(x, y));
            }
            for (int x = seam[y] + 1; x < this.width(); x++) {
                newPicture.setRGB(x - 1, y, this.picture.getRGB(x, y));
            }
        }

        this.setNewPicture(newPicture);
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

    private static double getDeltaSquared(int rgbA, int rgbB) {
        int aRed = (rgbA >> 16) & 0xFF;
        int aGreen = (rgbA >> 8) & 0xFF;
        int aBlue = (rgbA) & 0xFF;
        int bRed = (rgbB >> 16) & 0xFF;
        int bGreen = (rgbB >> 8) & 0xFF;
        int bBlue = (rgbB) & 0xFF;
        return Math.pow(aRed - bRed, 2) +
                Math.pow(aGreen - bGreen, 2) +
                Math.pow(aBlue - bBlue, 2);
    }

    private void setNewPicture(Picture newPicture) {
        this.energies = null;
        this.picture = newPicture;
    }
}
