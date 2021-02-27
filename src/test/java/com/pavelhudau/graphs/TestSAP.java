package com.pavelhudau.graphs;

import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSAP {
    @Test
    void testLengthWhenGraphIsTriangle() {
        SAP sap = new SAP(createTriangleDigraph());
        assertEquals(2, sap.length(1, 5));
        assertEquals(1, sap.length(2, 1));
        assertEquals(0, sap.length(2, 2));
    }

    @Test
    void testLengthWhenGraphIsTriangleAndNoPathExits() {
        SAP sap = new SAP(createDisconnectedDigraph());
        assertEquals(-1, sap.length(1, 4));
        assertEquals(-1, sap.length(1, 3));
        assertEquals(-1, sap.length(1, 5));
        assertEquals(-1, sap.length(0, 4));
        assertEquals(-1, sap.length(0, 3));
        assertEquals(-1, sap.length(0, 5));
        assertEquals(-1, sap.length(2, 4));
        assertEquals(-1, sap.length(2, 3));
        assertEquals(-1, sap.length(2, 5));
        assertEquals(-1, sap.length(4, 1));
        assertEquals(-1, sap.length(3, 1));
        assertEquals(-1, sap.length(5, 1));
        assertEquals(-1, sap.length(4, 0));
        assertEquals(-1, sap.length(3, 0));
        assertEquals(-1, sap.length(5, 0));
        assertEquals(-1, sap.length(4, 2));
        assertEquals(-1, sap.length(3, 2));
        assertEquals(-1, sap.length(5, 2));
    }

    @Test
    void testLengthWhenInvalidEdges() {
        SAP sap = new SAP(createTriangleDigraph());
        assertThrows(IllegalArgumentException.class, () -> sap.length(7, 1));
        assertThrows(IllegalArgumentException.class, () -> sap.length(1, 7));
    }

    @Test
    void testAncestorWhenGraphIsTriangle() {
        SAP sap = new SAP(createTriangleDigraph());
        assertEquals(0, sap.ancestor(1, 5));
        assertEquals(3, sap.ancestor(3, 3));
        assertEquals(3, sap.ancestor(3, 3));
        assertEquals(4, sap.ancestor(4, 2));
    }

    @Test
    void testAncestorWhenGraphIsTriangleAndNoPathExits() {
        SAP sap = new SAP(createDisconnectedDigraph());
        assertEquals(-1, sap.ancestor(1, 4));
        assertEquals(-1, sap.ancestor(1, 3));
        assertEquals(-1, sap.ancestor(1, 5));
        assertEquals(-1, sap.ancestor(0, 4));
        assertEquals(-1, sap.ancestor(0, 3));
        assertEquals(-1, sap.ancestor(0, 5));
        assertEquals(-1, sap.ancestor(2, 4));
        assertEquals(-1, sap.ancestor(2, 3));
        assertEquals(-1, sap.ancestor(2, 5));
        assertEquals(-1, sap.ancestor(4, 1));
        assertEquals(-1, sap.ancestor(3, 1));
        assertEquals(-1, sap.ancestor(5, 1));
        assertEquals(-1, sap.ancestor(4, 0));
        assertEquals(-1, sap.ancestor(3, 0));
        assertEquals(-1, sap.ancestor(5, 0));
        assertEquals(-1, sap.ancestor(4, 2));
        assertEquals(-1, sap.ancestor(3, 2));
        assertEquals(-1, sap.ancestor(5, 2));
    }

    @Test
    void testAncestorWhenInvalidEdges() {
        SAP sap = new SAP(createTriangleDigraph());
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(7, 1));
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(1, 7));
    }

    private static Digraph createTriangleDigraph() {
        Digraph di = new Digraph(6);
        di.addEdge(1, 2);
        di.addEdge(2, 3);
        di.addEdge(3, 4);
        di.addEdge(4, 5);
        di.addEdge(5, 0);
        di.addEdge(1, 0);
        return di;
    }

    private static Digraph createDisconnectedDigraph() {
        Digraph di = new Digraph(6);
        di.addEdge(0, 1);
        di.addEdge(2, 1);
        di.addEdge(3, 4);
        di.addEdge(5, 4);
        return di;
    }
}
