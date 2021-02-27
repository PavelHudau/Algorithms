package com.pavelhudau.graphs;

import edu.princeton.cs.algs4.Digraph;

/**
 * Checks that
 * 1. There is a root.
 * 2. All vertices have the same root.
 */
public class DirectedRootedCheck {
    private static final int NO_ROOT = -1;
    private final boolean rooted;
    private Digraph digraph;
    private Digraph digraphReverse;
    private boolean[] marked;

    public DirectedRootedCheck(Digraph digraph) {
        this.digraph = digraph;
        this.rooted = this.calculateIsRooted();
    }

    public boolean isRooted() {
        return this.rooted;
    }

    private boolean calculateIsRooted() {
        int root = this.findRootCandidate();
        if (root == NO_ROOT) {
            // If there is no root, then graph is not rooted.
            return false;
        }

        this.digraphReverse = this.digraph.reverse();
        this.marked = new boolean[this.digraphReverse.V()];
        // Run DFS on reverse graph starting from root, if all vertices get marked,
        // then there is a path from all vertices to the root.
        this.dfsOnReversedDigraph(root);
        for (boolean isMarked : marked) {
            if (!isMarked) {
                return false;
            }
        }

        this.marked = null;
        this.digraph = null;
        this.digraphReverse = null;

        return true;
    }

    private int findRootCandidate() {
        for (int v = 0; v < this.digraph.V(); v++) {
            if (this.digraph.outdegree(v) == 0) {
                return v;
            }
        }
        return NO_ROOT;
    }

    private void dfsOnReversedDigraph(int v) {
        this.marked[v] = true;
        for (int w : this.digraphReverse.adj(v)) {
            if (!this.marked[w]) {
                this.dfsOnReversedDigraph(w);
            }
        }
    }
}
