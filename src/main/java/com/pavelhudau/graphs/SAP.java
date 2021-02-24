package com.pavelhudau.graphs;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

/**
 * Shortest ancestral path.
 */
public class SAP {
    private final Digraph digraph;

    /**
     * Constructor takes a digraph (not necessarily a DAG)
     * @param G digraph
     */
    public SAP(Digraph G) {
        this.digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path

    /**
     *
     * @param v
     * @param w
     * @return
     */
    public int length(int v, int w) {
        this.validateVertex(v);
        this.validateVertex(w);

        throw new IllegalArgumentException("NOT IMPLEMENTED");
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path

    /**
     *
     * @param v
     * @param w
     * @return
     */
    public int ancestor(int v, int w) {
        this.validateVertex(v);
        this.validateVertex(w);

        throw new IllegalArgumentException("NOT IMPLEMENTED");
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path

    /**
     *
     * @param v
     * @param w
     * @return
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        throw new IllegalArgumentException("NOT IMPLEMENTED");
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path

    /**
     *
     * @param v
     * @param w
     * @return
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        throw new IllegalArgumentException("NOT IMPLEMENTED");
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= this.digraph.V()) {
            throw new IllegalArgumentException("v must be between 0 and " + this.digraph.V() + " but is " + v);
        }
    }

    private class BfsForVertex {
        private final Digraph digraph;
        private final int[] edgeTo;
        private final int[] distanceTo;
        private final boolean[] marked;

        private final int vertex;

        public BfsForVertex(Digraph digraph, int v) {
            this.digraph = digraph;
            this.vertex = v;
            this.distanceTo = new int[digraph.V()];
            this.edgeTo = new int[digraph.V()];
            this.marked = new boolean[digraph.V()];
            this.bfs();
        }

        private void bfs() {
            Queue<Integer> queue = new Queue<>();
            queue.enqueue(this.vertex);
            this.marked[this.vertex] = true;
            this.distanceTo[this.vertex] = 0;

            while (!queue.isEmpty()) {
                int v = queue.dequeue();
                for (int w : this.digraph.adj(v)) {
                    if (this.marked[w]) {
                        continue;
                    }
                    queue.enqueue(w);
                    this.distanceTo[w] = this.distanceTo[v] + 1;
                    this.edgeTo[w] = v;
                    this.marked[w] = true;
                }
            }
        }
    }
}
