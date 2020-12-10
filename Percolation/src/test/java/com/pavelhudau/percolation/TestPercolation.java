package com.pavelhudau.percolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestPercolation {
    @Test
    void testOpenSimple() {
        Percolation percolation = new Percolation(5);
        percolation.open(2, 3);
        percolation.isOpen(2, 3);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void testOpenEdgeCases(int n) {
        Percolation percolation = new Percolation(5);
        percolation.open(n, n);
        percolation.isOpen(n, n);
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
                assert percolation.isOpen(i, j) == (i == j);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 100})
    void testIsOpenNoOpenSites(int n) {
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                assert !percolation.isOpen(i, j);
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
                assert percolation.isOpen(i, j);
            }
        }
    }

    @Test
    void testIsFullWithSingleItemWhenIsFull() {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 2);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(3, 4);
        percolation.open(2, 4);
        percolation.open(1, 4);
        assert !percolation.isFull(1, 1);
        assert percolation.isFull(1, 2);
        assert percolation.isFull(3, 3);
    }

    @Test
    void testIsFullSimpleWhenIsFull() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        assert percolation.isFull(1, 1);
    }

    @Test
    void testisFullWithSingleItemWhenIsNotFull() {
        Percolation percolation = new Percolation(1);
        assert !percolation.isFull(1, 1);
    }

    @Test
    void testNumberOfOpenSitesSimple() {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 2);
        percolation.open(3, 2);
        assert percolation.numberOfOpenSites() == 3;
    }

    @Test
    void testNumberOfOpenSitesWhenDiagonalWithRepeats() {
        int n = 3;
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            percolation.open(2, 1);
            percolation.open(i, i);
        }
        assert percolation.numberOfOpenSites() == 4;
    }

    @Test
    void testPercolatesSimpleWhenPercolates() {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 2);
        percolation.open(3, 2);
        assert percolation.percolates();
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
        assert percolation.percolates();
        assert percolation.numberOfOpenSites() == 9;
    }

    @Test
    void testPercolatesSimpleWhenDoesNotPercolate() {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 1);
        percolation.open(3, 3);
        assert !percolation.percolates();
        assert percolation.numberOfOpenSites() == 3;
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
        assert !percolation.percolates();
    }

    @Test
    void testPercolatesWith1by1WhenPercolates() {
        Percolation percolation = new Percolation(1);
        percolation.open(1, 1);
        assert percolation.percolates();
    }

    @Test
    void testPercolatesWith1by1WhenDoesNotPercolate() {
        Percolation percolation = new Percolation(1);
        assert !percolation.percolates();
    }
}