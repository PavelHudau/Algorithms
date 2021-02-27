package com.pavelhudau.graphs;

import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
    void testLengthWhenTwoNodeDigraph() {
        SAP sap = new SAP(createTwoNodeDigraph());
        assertEquals(1, sap.length(0, 1));
        assertEquals(1, sap.length(1, 0));
    }

    @Test
    void testLengthWhenOneNodeDigraph() {
        SAP sap = new SAP(createOneNodeDigraph());
        assertEquals(0, sap.length(0, 0));
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

    @Test
    void testAncestorWhenTwoNodeDigraph() {
        SAP sap = new SAP(createTwoNodeDigraph());
        assertEquals(1, sap.ancestor(0, 1));
        assertEquals(1, sap.ancestor(1, 0));
    }

    @Test
    void testAncestorWhenOneNodeDigraph() {
        SAP sap = new SAP(createOneNodeDigraph());
        assertEquals(0, sap.ancestor(0, 0));
    }

    @Test
    void testLengthWithMultiNodes() {
        SAP sap = new SAP(createTreeLikeDigraph());
        assertEquals(4, sap.length(asIterable(new int[]{13, 14}), asIterable(new int[]{15, 16})));
        assertEquals(4, sap.length(asIterable(new int[]{13, 14}), asIterable(new int[]{22, 16})));
        assertEquals(3, sap.length(asIterable(new int[]{13, 5}), asIterable(new int[]{4, 24})));
        assertEquals(4, sap.length(asIterable(new int[]{13, 23, 24}), asIterable(new int[]{6, 16, 17})));
    }

    @Test
    void testLengthWithMultiNodesWhenVerticesIsdNullThenThrowsException() {
        SAP sap = new SAP(createTreeLikeDigraph());
        assertThrows(IllegalArgumentException.class, () -> sap.length(null, asIterable(new int[]{15, 16})));
        assertThrows(IllegalArgumentException.class, () -> sap.length(asIterable(new int[]{13, 14}), null));
    }

    @Test
    void testAncestorWithMultiNodes() {
        SAP sap = new SAP(createTreeLikeDigraph());
        assertEquals(3, sap.ancestor(asIterable(new int[]{13, 14}), asIterable(new int[]{15, 16})));
        assertEquals(3, sap.ancestor(asIterable(new int[]{13, 14}), asIterable(new int[]{22, 16})));
        assertEquals(5, sap.ancestor(asIterable(new int[]{13, 5}), asIterable(new int[]{4, 24})));
        assertEquals(3, sap.ancestor(asIterable(new int[]{13, 23, 24}), asIterable(new int[]{6, 16, 17})));
    }

    @Test
    void testAncestorWithMultiNodesWhenVerticesIsdNullThenThrowsException() {
        SAP sap = new SAP(createTreeLikeDigraph());
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(null, asIterable(new int[]{15, 16})));
        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(asIterable(new int[]{13, 14}), null));
    }

    /**
     * Triangle digraph is taken from https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php.
     *
     * @return Triangle digraph.
     */
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

    private static Digraph createTwoNodeDigraph() {
        Digraph di = new Digraph(2);
        di.addEdge(0, 1);
        return di;
    }

    private static Digraph createOneNodeDigraph() {
        Digraph di = new Digraph(1);
        di.addEdge(0, 0);
        return di;
    }

    /**
     * Tree like digraph is taken from https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php.
     *
     * @return Tree like digraph.
     */
    private static Digraph createTreeLikeDigraph() {
        Digraph di = new Digraph(25);
        di.addEdge(1, 0);
        di.addEdge(2, 0);
        di.addEdge(3, 1);
        di.addEdge(4, 1);
        di.addEdge(7, 3);
        di.addEdge(8, 3);
        di.addEdge(9, 3);
        di.addEdge(13, 7);
        di.addEdge(14, 7);
        di.addEdge(15, 9);
        di.addEdge(16, 9);
        di.addEdge(21, 16);
        di.addEdge(22, 16);
        di.addEdge(5, 2);
        di.addEdge(6, 2);
        di.addEdge(10, 5);
        di.addEdge(11, 5);
        di.addEdge(12, 5);
        di.addEdge(17, 10);
        di.addEdge(18, 10);
        di.addEdge(19, 12);
        di.addEdge(20, 12);
        di.addEdge(23, 20);
        di.addEdge(24, 20);
        return di;
    }

    private static Iterable<Integer> asIterable(int[] array) {
        return () -> Arrays.stream(array).iterator();
    }
}