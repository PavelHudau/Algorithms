package com.pavelhudau.percolation;

public class Percolation {
    private static final int BLOCKED_VALUE = -1;
    private static final int NOT_A_ROOT = 0;
    private final int n;
    private final int[] rootToTreeSize;
    private final int[] ids;
    private int openSitesCnt = 0;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be > 0");
        }
        this.n = n;
        this.ids = new int[n * n + 2];
        this.rootToTreeSize = new int[this.ids.length];
        this.ids[0] = 0;
        this.ids[this.ids.length - 1] = this.ids.length - 1;
        this.rootToTreeSize[0] = 1;
        this.rootToTreeSize[this.rootToTreeSize.length - 1] = 1;
        for (int i = 1; i < this.ids.length - 1; i++) {
            this.ids[i] = BLOCKED_VALUE;
            this.rootToTreeSize[i] = 1;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (this.isOpen(row, col)) {
            return;
        }

        int i = this.rowColToIdx(row, col);
        if (!this.isOpenIdx(i)) {
            this.ids[i] = i;
        }

        this.unionLeft(row, col, i);
        this.unionRight(row, col, i);
        this.unionTop(row, col, i);
        this.unionBottom(row, col, i);

        openSitesCnt++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int i = this.rowColToIdx(row, col);
        return this.isOpenIdx(i);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // Is open and connected to the top row
        int i = this.rowColToIdx(row, col);
        if (!this.isOpenIdx(i)) {
            return false;
        }

        int rooti = this.root(i);
        for (int topCol = 1; topCol <= this.n ; topCol++) {
            int topColI = this.rowColToIdx(1, topCol);
            if (!this.isOpenIdx(topColI)) {
                continue;
            }
            if (this.root(topColI) == rooti) {
                return true;
            }
        }

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCnt;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.root(0) == this.root(this.ids.length - 1);
    }

    private int rowColToIdx(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row must ne between withing interval [1, n]");
        }
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("col must ne between withing interval [1, n]");
        }
        return col + this.n * (row - 1);
    }

    private void union(int i, int j) {
        int rooti = this.root(i);
        int rootj = this.root(j);
        if (rooti == rootj) {
            return;
        }

        int sizei = this.size(rooti);
        int sizej = this.size(rootj);
        if (sizei > sizej) {
            this.ids[rootj] = rooti;
            this.rootToTreeSize[rooti] = sizei + sizej;
            this.rootToTreeSize[rootj] = NOT_A_ROOT;
        } else {
            this.ids[rooti] = rootj;
            this.rootToTreeSize[rootj] = sizei + sizej;
            this.rootToTreeSize[rooti] = NOT_A_ROOT;
        }
    }

    private int size(int root) {
        if (this.rootToTreeSize[root] != NOT_A_ROOT) {
            return this.rootToTreeSize[root];
        }

        throw new IllegalStateException(root + " is not a root index.");
    }

    private int root(int i) {
        if (i >= this.ids.length || i < 0) {
            throw new IllegalArgumentException("i must ne between withing interval [1, this.ids.length]");
        }

        while (i != this.ids[i]) {
            // flattens the tree
            this.ids[i] = this.ids[this.ids[i]];
            i = this.ids[i];
        }

        return i;
    }

    private boolean isOpenIdx(int i) {
        return this.ids[i] != BLOCKED_VALUE;
    }

    private void unionLeft(int row, int col, int iToUnionWith) {
        int leftCol = col - 1;
        if (leftCol < 1) {
            return;
        }

        int leftIdx = this.rowColToIdx(row, leftCol);
        if (!this.isOpenIdx(leftIdx)) {
            return;
        }

        this.union(iToUnionWith, leftIdx);
    }

    private void unionRight(int row, int col, int iToUnionWith) {
        int rightCol = col + 1;
        if (rightCol > this.n) {
            return;
        }

        int rightIdx = this.rowColToIdx(row, rightCol);
        if (!this.isOpenIdx(rightIdx)) {
            return;
        }

        this.union(iToUnionWith, rightIdx);
    }

    private void unionTop(int row, int col, int iToUnionWith) {
        int topRow = row - 1;
        if (topRow < 1) {
            // union with virtual top
            this.union(iToUnionWith, 0);
            return;
        }

        int topIdx = this.rowColToIdx(topRow, col);
        if (this.isOpenIdx(topIdx)) {
            this.union(iToUnionWith, topIdx);
        }
    }

    private void unionBottom(int row, int col, int iToUnionWith) {
        int bottomRow = row + 1;
        if (bottomRow > this.n) {
            // union with virtual bottom
            this.union(iToUnionWith, this.ids.length - 1);
            return;
        }

        int bottomIdx = this.rowColToIdx(bottomRow, col);
        if (this.isOpenIdx(bottomIdx)) {
            this.union(iToUnionWith, bottomIdx);
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 1);
        percolation.open(3, 3);
        assert !percolation.percolates();
        assert percolation.numberOfOpenSites() == 3;

        percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 2);
        percolation.open(3, 2);
        assert percolation.percolates();
        assert percolation.numberOfOpenSites() == 3;

        percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(4, 3);
        percolation.open(4, 4);
        percolation.open(4, 5);
        percolation.open(5, 5);
        assert percolation.percolates();
        assert percolation.numberOfOpenSites() == 9;

        percolation = new Percolation(100);
        for (int i = 1; i <= 100; i++) {
            percolation.open(1, 1);
            percolation.open(100, 100);
            percolation.open(i, i);
        }
        assert !percolation.percolates();
        assert percolation.numberOfOpenSites() == 100;
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                assert percolation.isOpen(i, i) == (i == j);
            }
        }
    }
}
