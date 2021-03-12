package com.pavelhudau.seamcarving;

class TopologicalShortestVerticalPath {
    private static final int NOT_VISITED = -1;
    private final double[][] distanceTo;
    private final int[][] xOfVertexFrom;
    private final double[][] energies;
    private final int width;
    private final int height;

    public TopologicalShortestVerticalPath(double[][] energies) {
        this.energies = energies;
        this.width = this.energies.length;
        this.height = this.energies[0].length;
        this.distanceTo = new double[energies.length][energies[0].length];
        this.xOfVertexFrom = new int[energies.length][energies[0].length];
    }

    public int[] findShortestPath() {
        this.reset();

        int[] shortestPath = new int[this.height];
        if(this.height > 1) {
            for (int y = 0; y < this.height - 1; y++) {
                for (int x = 0; x < this.width; x++) {
                    this.relaxVertex(x, y);
                }
            }

            // Construct shortest path walking bottom to top by xOfVertexFrom starting from X
            // with shortest distance.
            shortestPath[this.height - 1] = this.findMinPathEndX();
            for (int y = this.height - 1; y >= 1; y--) {
                int x = shortestPath[y];
                shortestPath[y - 1] = this.xOfVertexFrom[x][y];
            }
        }
        else {
            for (int y = 0; y < this.height; y++) {
                shortestPath[y] = y;
            }
        }

        return shortestPath;
    }

    private void relaxVertex(int x, int y) {
        int[] adjuscentXs;
        if (x == 0) {
            // left
            adjuscentXs = new int[]{x, x + 1};
        } else if (x == this.width - 1) {
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
        for (int x = 0; x < this.width; x++) {
            this.distanceTo[x][0] = this.energies[x][0];
        }

        for (int x = 0; x < this.width; x++) {
            for (int y = 1; y < this.height; y++) {
                this.distanceTo[x][y] = Double.POSITIVE_INFINITY;
                this.xOfVertexFrom[x][y] = NOT_VISITED;
            }
        }
    }

    private int findMinPathEndX() {
        int minPathEndX = 0;
        int bottomRowY = this.height - 1;
        for (int x = 1; x < this.width; x++) {
            if (Double.compare(this.distanceTo[minPathEndX][bottomRowY], this.distanceTo[x][bottomRowY]) > 0) {
                minPathEndX = x;
            }
        }
        return minPathEndX;
    }
}
