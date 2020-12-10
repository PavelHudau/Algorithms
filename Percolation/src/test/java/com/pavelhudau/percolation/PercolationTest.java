package com.pavelhudau.percolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PercolationTest {

    @Test
    void open_simple() {
        Percolation percolation = new Percolation(5);
        percolation.open(2, 3);
        percolation.isOpen(2, 3);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void open_edge_cases(int n) {
        Percolation percolation = new Percolation(5);
        percolation.open(n, n);
        percolation.isOpen(n, n);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 100})
    void isOpen_diagonal(int n) {
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
    void isOpen_no_open_sites(int n) {
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                assert !percolation.isOpen(i, j);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 100})
    void isOpen_all_sites_are_open(int n) {
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
    void isFull_single_item_is_full() {
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
    void isFull_simple_is_full() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        assert percolation.isFull(1, 1);
    }

    @Test
    void isFull_single_item_is_not_full() {
        Percolation percolation = new Percolation(1);
        assert !percolation.isFull(1, 1);
    }

    @Test
    void numberOfOpenSites_simple() {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 2);
        percolation.open(3, 2);
        assert percolation.numberOfOpenSites() == 3;
    }

    @Test
    void numberOfOpenSites_diagonal_with_repeats() {
        int n = 3;
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            percolation.open(2, 1);
            percolation.open(i, i);
        }
        assert percolation.numberOfOpenSites() == 4;
    }

    @Test
    void percolates_simple_percolates() {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 2);
        percolation.open(3, 2);
        assert percolation.percolates();
    }

    @Test
    void percolates_diagonal_percolates() {
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
    void percolates_simple_does_not_percolate() {
        Percolation percolation = new Percolation(3);
        percolation.open(2, 2);
        percolation.open(1, 1);
        percolation.open(3, 3);
        assert !percolation.percolates();
        assert percolation.numberOfOpenSites() == 3;
    }

    @Test
    void percolates_diagonal_does_not_percolates() {
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
    void percolates_1_by_1_percolates() {
        Percolation percolation = new Percolation(1);
        percolation.open(1, 1);
        assert percolation.percolates();
    }

    @Test
    void percolates_1_by_1_does_not_percolate() {
        Percolation percolation = new Percolation(1);
        assert !percolation.percolates();
    }
}