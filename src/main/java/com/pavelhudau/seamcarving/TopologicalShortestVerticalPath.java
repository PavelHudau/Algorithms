package com.pavelhudau.seamcarving;

public class TopologicalShortestVerticalPath {
    private final static int NOT_VISITED = -1;
    private final SeamCarver seamCarver;
    private final double[][] distanceTo;
    private final int[][] xOfVertexFrom;
    private final double[][] energies;

    public TopologicalShortestVerticalPath(SeamCarver seamCarver) {
        this.seamCarver = seamCarver;
        this.distanceTo = new double[this.seamCarver.width()][this.seamCarver.height()];
        this.xOfVertexFrom = new int[this.seamCarver.width()][this.seamCarver.height()];
        this.energies = new double[this.seamCarver.width()][this.seamCarver.height()];

        for (int x = 0; x < this.distanceTo.length; x++) {
            for (int y = 0; y < this.distanceTo[x].length; y++) {
                this.energies[x][y] = this.seamCarver.energy(x, y);
            }
        }
    }

    public int[] findShortestPath() {
        this.reset();

        for (int y = 0; y < this.distanceTo[0].length - 1; y++) {
            for (int x = 0; x < this.distanceTo.length; x++) {
                this.relaxVertex(x, y);
            }
        }

        // Construct shortest path by walking back xOfVertexFrom starting from X
        // which distance is shortest.
        int[] shortestPath = new int[this.xOfVertexFrom[0].length];
        shortestPath[this.xOfVertexFrom[0].length - 1] = this.findMinPathEndX();
        for (int y = shortestPath.length - 1; y >= 1; y--) {
            int x = shortestPath[y];
            shortestPath[y - 1] = this.xOfVertexFrom[x][y];
        }

        return shortestPath;
    }

    private void relaxVertex(int x, int y) {
        int[] adjuscentXs;
        if (x == 0){
            // left
            adjuscentXs = new int[]{x, x + 1};
        }
        else if (x == this.distanceTo.length - 1) {
            // right
            adjuscentXs = new int[]{x, x - 1};
        } else {
            // middle
            adjuscentXs = new int[]{x, x - 1, x + 1};
        }

        int adjuscentY = y + 1;
        for (int adjuscentX : adjuscentXs) {
            double newDistance = this.distanceTo[x][y] + this.energies[adjuscentX][adjuscentY];
            if (Double.compare(this.distanceTo[adjuscentX][adjuscentY], newDistance) > 0) {
                this.distanceTo[adjuscentX][adjuscentY] = newDistance;
                this.xOfVertexFrom[adjuscentX][adjuscentY] = x;
            }
        }
    }

    private void reset() {
        for (int x = 0; x < this.distanceTo.length; x++) {
            this.distanceTo[x][0] = this.seamCarver.energy(x, 0);
        }
        for (int x = 0; x < this.distanceTo.length; x++) {
            for (int y = 1; y < this.distanceTo[x].length; y++) {
                this.distanceTo[x][y] = Double.POSITIVE_INFINITY;
                this.xOfVertexFrom[x][y] = NOT_VISITED;
            }
        }
    }

    private int findMinPathEndX() {
        int minPathEndX = 0;
        int bottomRowY = this.distanceTo[0].length - 1;
        for (int x = 1; x < this.distanceTo.length; x++) {
            if (Double.compare(this.distanceTo[minPathEndX][bottomRowY], this.distanceTo[x][bottomRowY]) > 0) {
                minPathEndX = x;
            }
        }
        return minPathEndX;
    }
}
