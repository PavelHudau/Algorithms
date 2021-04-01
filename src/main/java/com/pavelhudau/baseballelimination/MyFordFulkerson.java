package com.pavelhudau.baseballelimination;

import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class MyFordFulkerson {
    private final FlowNetwork network;
    private final FlowEdge[] edgeTo;
    private final boolean[] visited;
    private Queue<Integer> vertices;
    private double networkFlow = 0.0;

    public MyFordFulkerson(FlowNetwork network) {
        this.network = network;
        this.vertices = new Queue<>();
        this.edgeTo = new FlowEdge[this.network.V()];
        this.visited = new boolean[this.network.V()];
    }

    public void run(int source, int target) {
        while (this.hasAugmentingPathUsingBfs(source, target)) {
            double bottleneck = this.computeBottleneckCapacity(source, target);
            this.applyBottleneckCapacity(source, target, bottleneck);
            networkFlow += bottleneck;
        }
    }

    public double flow() {
        return this.networkFlow;
    }

    /**
     * Determines whether vertex is in min cut after running Ford Fulkerson.
     *
     * @param vertex A vertex.
     * @return True if vertex is in min cut, else False.
     */
    public boolean inMinCut(int vertex) {
        // After running Ford Fulkerson algorithm the last run will not be able to find Augmenting Path and will
        // mark all vertices that are reachable from source by following edges that still have residual value.
        // By definition min cut is a set of vertices connected to source by an undirected path
        // with no full forward or empty backward edges (residual value is greater than 0). And this is exactly how we
        // run BFS to find Augmenting Path.
        return this.visited[vertex];
    }

    private boolean hasAugmentingPathUsingBfs(int source, int target) {
        this.resetBfs();

        this.vertices.enqueue(source);
        this.visited[source] = true;

        // Run BFS to see if there is Augmenting path from source to target.
        while (!this.vertices.isEmpty()) {
            int currentVertex = this.vertices.dequeue();
            // We could stop by reaching target, but then we may not discover all paths to other vertices
            // which we will need to understand min cut.
            // if (currentVertex == target) {
            //     return true;
            // }

            for (FlowEdge currentEdge : this.network.adj(currentVertex)) {
                int vertexTo = currentEdge.other(currentVertex);
                if (!this.visited[vertexTo] && currentEdge.residualCapacityTo(vertexTo) > 0.0) {
                    this.vertices.enqueue(vertexTo);
                    this.visited[vertexTo] = true;
                    this.edgeTo[vertexTo] = currentEdge;
                }
            }
        }

        return this.visited[target];
    }

    private double computeBottleneckCapacity(int source, int target) {
        double bottleneckCapacity = Double.POSITIVE_INFINITY;
        int currentVertex = target;
        while (currentVertex != source) {
            FlowEdge currentEdge = this.edgeTo[currentVertex];
            bottleneckCapacity = Math.min(currentEdge.residualCapacityTo(currentVertex), bottleneckCapacity);
            currentVertex = currentEdge.other(currentVertex);
        }

        return bottleneckCapacity;
    }

    private void applyBottleneckCapacity(int source, int target, double bottleneckCapacity) {
        int currentVertex = target;
        while (currentVertex != source) {
            FlowEdge currentEdge = this.edgeTo[currentVertex];
            this.edgeTo[currentVertex].addResidualFlowTo(currentVertex, bottleneckCapacity);
            currentVertex = currentEdge.other(currentVertex);
        }
    }

    private void resetBfs() {
        this.vertices = new Queue<>();
        Arrays.fill(this.edgeTo, null);
        Arrays.fill(this.visited, false);
    }
}
