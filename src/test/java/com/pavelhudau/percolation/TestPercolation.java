package com.pavelhudau.percolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestPercolation {
    @Test
    void testOpenSimple() {
        Percolation percolation = new Percolation(5);
        percolation.open(2, 3);
        assertTrue(percolation.isOpen(2, 3));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void testOpenEdgeCases(int n) {
        Percolation percolation = new Percolation(5);
        percolation.open(n, n);
        assertTrue(percolation.isOpen(n, n));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 100})
    void testIsOpenDiagonal(int n) {
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            percolation.open(i, i);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                assertEquals(i == j, percolation.isOpen(i, j));
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 100})
    void testIsOpenNoOpenSites(int n) {
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                assertFalse(percolation.isOpen(i, j));
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 100})
    void testIsOpenAllSitesAreOpen(int n) {
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                percolation.open(i, j);
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                assertTrue(percolation.isOpen(i, j));
            }
        }
    }

    @Test
    void testIsFullWithHorseShoe() {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 2);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(3, 4);
        percolation.open(2, 4);
        percolation.open(1, 4);
        percolation.open(5, 4);
        assertTrue(percolation.isFull(1, 2));
        assertTrue(percolation.isFull(3, 3));
        assertFalse(percolation.isFull(1, 1));
        assertFalse(percolation.isFull(5, 4));
    }

    @Test
    void testIsFullWithSnake() {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 4);
        percolation.open(4, 4);
        percolation.open(4, 5);
        percolation.open(5, 2);
        assertTrue(percolation.isFull(4, 4));
        assertTrue(percolation.isFull(2, 3));
        assertTrue(percolation.isFull(1, 2));
        assertTrue(percolation.isFull(1, 1));
        assertFalse(percolation.isFull(1, 3));
        assertFalse(percolation.isFull(5, 2));
        assertFalse(percolation.isFull(5, 4));
        assertFalse(percolation.isFull(4, 3));
    }

    @Test
    void testIsFullTwoLinesBottomToTop() {
        Percolation percolation = new Percolation(5);
        for (int row = 5; row > 0; row--) {
            percolation.open(row, 2);
            percolation.open(row, 4);
        }

        assertTrue(percolation.isFull(5, 2));
        assertFalse(percolation.isFull(5, 3));
    }

    @Test
    void testIsFullSimpleWhenIsFull() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        assertTrue(percolation.isFull(1, 1));
    }

    @Test
    void testisFullWithSingleItemWhenIsNotFull() {
        Percolation percolation = new Percolation(1);
        assertFalse(percolation.isFull(1, 1));
    }

    @Test
    void testNumberOfOpenSitesSimple() {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 2);
        percolation.open(3, 2);
        assertEquals(3, percolation.numberOfOpenSites());
    }

    @Test
    void testNumberOfOpenSitesWhenDiagonalWithRepeats() {
        int n = 3;
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            percolation.open(2, 1);
            percolation.open(i, i);
        }
        assertEquals(4, percolation.numberOfOpenSites());
    }

    @Test
    void testPercolatesSimpleWhenPercolates() {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 2);
        percolation.open(3, 2);
        assertTrue(percolation.percolates());
    }

    @Test
    void testPercolatesWithDiagonalWhenPercolates() {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(4, 3);
        percolation.open(4, 4);
        percolation.open(4, 5);
        percolation.open(5, 5);
        assertTrue(percolation.percolates());
        assertEquals(9, percolation.numberOfOpenSites());
    }

    @Test
    void testPercolatesSimpleWhenDoesNotPercolate() {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 1);
        percolation.open(3, 3);
        assertFalse(percolation.percolates());
        assertEquals(3, percolation.numberOfOpenSites());
    }

    @Test
    void testPercolatesDiagonalWhenDoesNotPercolates() {
        int n = 5;
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            percolation.open(1, 1);
            percolation.open(n, n);
            percolation.open(i, i);
        }
        assertFalse(percolation.percolates());
    }

    @Test
    void testPercolatesWith1by1WhenPercolates() {
        Percolation percolation = new Percolation(1);
        percolation.open(1, 1);
        assertTrue(percolation.percolates());
    }

    @Test
    void testPercolatesWith1by1WhenDoesNotPercolate() {
        Percolation percolation = new Percolation(1);
        assertFalse(percolation.percolates());
    }
}