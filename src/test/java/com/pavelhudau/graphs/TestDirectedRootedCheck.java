package com.pavelhudau.graphs;

import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDirectedRootedCheck {
    @Test
    void testIsRootedWhenTwoNodesAndRooted() {
        Digraph digraph = new Digraph(2);
        digraph.addEdge(1, 0);
        DirectedRootedCheck rootedCheck = new DirectedRootedCheck(digraph);
        assertTrue(rootedCheck.isRooted());
    }

    @Test
    void testIsRootedWhenThreeNodesAndRooted() {
        Digraph digraph = new Digraph(3);
        digraph.addEdge(1, 0);
        digraph.addEdge(2, 0);
        DirectedRootedCheck rootedCheck = new DirectedRootedCheck(digraph);
        assertTrue(rootedCheck.isRooted());
    }

    @Test
    void testIsRootedWhenThereAreSkippingEdgesAndRooted() {
        Digraph digraph = new Digraph(4);
        digraph.addEdge(1, 0);
        digraph.addEdge(2, 0);
        digraph.addEdge(3, 2);
        digraph.addEdge(3, 0);
        DirectedRootedCheck rootedCheck = new DirectedRootedCheck(digraph);
        assertTrue(rootedCheck.isRooted());
    }

    @Test
    void testIsRootedWhenThereIsNoRoot() {
        Digraph digraph = new Digraph(3);
        digraph.addEdge(1, 0);
        digraph.addEdge(0, 2);
        digraph.addEdge(2, 1);
        DirectedRootedCheck rootedCheck = new DirectedRootedCheck(digraph);
        assertFalse(rootedCheck.isRooted());
    }

    @Test
    void testIsRootedWhenThereAreFewRoots() {
        Digraph digraph = new Digraph(5);
        digraph.addEdge(1, 0);
        digraph.addEdge(2, 0);
        digraph.addEdge(2, 3);
        digraph.addEdge(4, 3);
        DirectedRootedCheck rootedCheck = new DirectedRootedCheck(digraph);
        assertFalse(rootedCheck.isRooted());
    }
}