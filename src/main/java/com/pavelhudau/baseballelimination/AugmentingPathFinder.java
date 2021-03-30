package com.pavelhudau.baseballelimination;

import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class AugmentingPathFinder {
    private final FlowNetwork network;
    private final FlowEdge[] edgeFrom;
    private final boolean[] visited;
    private Queue<Integer> vertices;

    public AugmentingPathFinder(FlowNetwork network) {
        this.network = network;
        this.vertices = new Queue<>();
        this.edgeFrom = new FlowEdge[this.network.V()];
        this.visited = new boolean[this.network.V()];
    }

    public boolean findPath(int source, int target) {
        this.reset();
        boolean foundPath = this.hasAugmentingPathUsingBfs(source, target);
        if (foundPath) {
            this.applyBottleneckCapacity(source, target, this.computeBottleneckCapacity(source, target));
        }

        return foundPath;
    }

    private boolean hasAugmentingPathUsingBfs(int source, int target) {
        this.vertices.enqueue(source);
        while (!this.vertices.isEmpty()) {
            int currentVertex = this.vertices.dequeue();
            if (currentVertex == target) {
                return true;
            }

            for (FlowEdge currentEdge : this.network.adj(currentVertex)) {
                int vertexTo = currentEdge.to();
                if (this.visited[vertexTo] || currentEdge.residualCapacityTo(vertexTo) <= 0.0) {
                    continue;
                }

                this.vertices.enqueue(vertexTo);
                this.visited[vertexTo] = true;
                this.edgeFrom[vertexTo] = currentEdge;
            }
        }

        return false;
    }

    private double computeBottleneckCapacity(int source, int target) {
        double bottleneckCapacity = Double.POSITIVE_INFINITY;
        int currentVertex = target;
        while (currentVertex != source) {
            FlowEdge currentEdge = this.edgeFrom[currentVertex];
            bottleneckCapacity = Math.min(currentEdge.residualCapacityTo(currentVertex), bottleneckCapacity);
            currentVertex = currentEdge.other(currentVertex);
        }

        return bottleneckCapacity;
    }

    private void applyBottleneckCapacity(int source, int target, double bottleneckCapacity) {
        int currentVertex = target;
        while (currentVertex != source) {
            FlowEdge currentEdge = this.edgeFrom[currentVertex];
            this.edgeFrom[currentVertex].addResidualFlowTo(currentVertex, bottleneckCapacity);
            currentVertex = currentEdge.other(currentVertex);
        }
    }

    private void reset() {
        this.vertices = new Queue<>();
        Arrays.fill(this.edgeFrom, null);
        Arrays.fill(this.visited, false);
    }
}
