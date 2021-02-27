package com.pavelhudau.graphs;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;

import java.util.Arrays;
import java.util.Collections;

/**
 * Shortest ancestral path.
 */
public class SAP {
    private static final int NOT_FOUND = -1;
    private final Digraph digraph;
    private int lastShortestDistance;
    private int lastClosestAncestor;
    private SET<Integer> lastVs = new SET<>();
    private SET<Integer> lastWs = new SET<>();

    /**
     * Constructor takes a digraph (not necessarily a DAG)
     *
     * @param G digraph
     */
    public SAP(Digraph G) {
        this.digraph = G;
    }

    /**
     * Length of shortest ancestral path between v and w; -1 if no such path.
     *
     * @param v First vertex.
     * @param w Second vertex.
     * @return Length of shortest ancestral path between v and w; -1 if no path found between v and w.
     */
    public int length(int v, int w) {
        this.runBfsForVertices(Collections.singletonList(v), Collections.singletonList(w));
        return this.lastShortestDistance;
    }

    /**
     * A common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path.
     *
     * @param v First vertex.
     * @param w Second vertex.
     * @return Common ancestor vertex; -1 if no path found between v and w.
     */
    public int ancestor(int v, int w) {
        this.runBfsForVertices(Collections.singletonList(v), Collections.singletonList(w));
        return this.lastClosestAncestor;
    }


    /**
     * Length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path.
     *
     * @param v First set of vertices.
     * @param w Second set of vertices.
     * @return shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path.
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        this.runBfsForVertices(v, w);
        return this.lastShortestDistance;
    }

    /**
     * A common ancestor that participates in shortest ancestral path; -1 if no such path.
     *
     * @param v First set of vertices.
     * @param w Second set of vertices.
     * @return Common ancestor vertex; -1 if no path found between sets v and w.
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        this.runBfsForVertices(v, w);
        return this.lastClosestAncestor;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= this.digraph.V()) {
            throw new IllegalArgumentException("v must be between 0 and " + this.digraph.V() + " but is " + v);
        }
    }

    private void validateVertices(Iterable<Integer> vs) {
        if (vs == null) {
            throw new IllegalArgumentException("Vertices can not be null");
        }
        for (int v : vs) {
            this.validateVertex(v);
        }
    }

    private void runBfsForVertices(Iterable<Integer> vs, Iterable<Integer> ws) {
        this.validateVertices(vs);
        this.validateVertices(ws);
        SET<Integer> newVs = toSet(vs);
        SET<Integer> newWs = toSet(ws);
        if (!newWs.equals(this.lastVs) || !newWs.equals(this.lastWs)) {
            BfsForVertex vBfs = new BfsForVertex(this.digraph, newVs);
            BfsForVertex wBfs = new BfsForVertex(this.digraph, newWs);
            this.findCommonAncestor(vBfs, wBfs);
            this.lastVs = newVs;
            this.lastWs = newWs;
        }
    }

    private void findCommonAncestor(BfsForVertex vBfs, BfsForVertex wBfs) {
        this.lastShortestDistance = NOT_FOUND;
        this.lastClosestAncestor = NOT_FOUND;
        for (int v = 0; v < vBfs.distanceTo.length; v++) {
            if (vBfs.isMarked(v) && wBfs.isMarked(v)) {
                int distance = vBfs.distanceTo[v] + wBfs.distanceTo[v];
                if (this.lastShortestDistance == NOT_FOUND || this.lastShortestDistance > distance) {
                    this.lastClosestAncestor = v;
                    this.lastShortestDistance = distance;
                }
            }
        }
    }

    private static SET<Integer> toSet(Iterable<Integer> iterable) {
        SET<Integer> set = new SET<>();
        for (int it : iterable) {
            set.add(it);
        }

        return set;
    }

    private static class BfsForVertex {
        public final static int NOT_MARKED = -1;
        private final Digraph digraph;
        private final int[] distanceTo;

        public BfsForVertex(Digraph digraph, Iterable<Integer> startVertices) {
            this.digraph = digraph;
            this.distanceTo = new int[digraph.V()];
            Arrays.fill(this.distanceTo, NOT_MARKED);
            this.bfs(startVertices);
        }

        public boolean isMarked(int v) {
            return this.distanceTo[v] != NOT_MARKED;
        }

        private void bfs(Iterable<Integer> startVertices) {
            Queue<Integer> queue = new Queue<>();
            for (int v : startVertices) {
                queue.enqueue(v);
                this.distanceTo[v] = 0;
            }

            while (!queue.isEmpty()) {
                int v = queue.dequeue();
                for (int w : this.digraph.adj(v)) {
                    if (this.distanceTo[w] == NOT_MARKED) {
                        queue.enqueue(w);
                        this.distanceTo[w] = this.distanceTo[v] + 1;
                    }
                }
            }
        }
    }
}
