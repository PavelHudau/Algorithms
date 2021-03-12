package com.pavelhudau.seamcarving;

class TopologicalShortestHorizontalPath {
    private static final int NOT_VISITED = -1;
    private final double[][] distanceTo;
    private final int[][] yOfHorizontalFrom;
    private final double[][] energies;
    private final int width;
    private final int height;

    public TopologicalShortestHorizontalPath(double[][] energies) {
        this.energies = energies;
        this.width = this.energies.length;
        this.height = this.energies[0].length;
        this.distanceTo = new double[this.width][this.height];
        this.yOfHorizontalFrom = new int[this.width][this.height];
    }

    public int[] findShortestPath() {
        this.reset();

        for (int x = 0; x < this.width - 1; x++) {
            for (int y = 0; y < this.height; y++) {
                this.relaxVertex(x, y);
            }
        }

        // Construct shortest path walking right to left by yOfHorizontalFrom Y
        // with shortest distance.
        int[] shortestPath = new int[this.width];
        shortestPath[this.width - 1] = this.findMinPathEndY();
        for (int x = this.width - 1; x >= 1; x--) {
            int y = shortestPath[x];
            shortestPath[x - 1] = this.yOfHorizontalFrom[x][y];
        }

        return shortestPath;
    }

    private void relaxVertex(int x, int y) {
        int[] adjuscentYs;
        if (y == 0) {
            // top
            adjuscentYs = new int[]{y, y + 1};
        } else if (y == this.height - 1) {
            // bottom
            adjuscentYs = new int[]{y, y - 1};
        } else {
            // middle
            adjuscentYs = new int[]{y, y - 1, y + 1};
        }

        int adjuscentX = x + 1;
        for (int adjuscentY : adjuscentYs) {
            double newDistance = this.distanceTo[x][y] + this.energies[adjuscentX][adjuscentY];
            if (Double.compare(this.distanceTo[adjuscentX][adjuscentY], newDistance) > 0) {
                this.distanceTo[adjuscentX][adjuscentY] = newDistance;
                this.yOfHorizontalFrom[adjuscentX][adjuscentY] = y;
            }
        }
    }

    private void reset() {
        System.arraycopy(this.energies[0], 0, this.distanceTo[0], 0, this.height);

        for (int x = 1; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.distanceTo[x][y] = Double.POSITIVE_INFINITY;
                this.yOfHorizontalFrom[x][y] = NOT_VISITED;
            }
        }
    }

    private int findMinPathEndY() {
        int minPathEndY = 0;
        int rightColumnX = this.width - 1;
        for (int y = 1; y < this.height; y++) {
            if (Double.compare(this.distanceTo[rightColumnX][minPathEndY], this.distanceTo[rightColumnX][y]) > 0) {
                minPathEndY = y;
            }
        }
        return minPathEndY;
    }
}
