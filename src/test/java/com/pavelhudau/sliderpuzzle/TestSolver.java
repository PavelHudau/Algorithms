package com.pavelhudau.sliderpuzzle;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestSolver {
    @Test()
    void testStepByStepWhenThereIsSolution() {
        Solver solver = new Solver(new Board(new int[][]{
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        }));
        assertTrue(solver.isSolvable());
        assertEquals(5, solver.moves());
        assertNotNull(solver.solution());
        int move = 0;
        for (Board solution : solver.solution()) {
            Board expected;
            switch (move) {
                case 0:
                    expected = new Board(new int[][]{
                            {0, 1, 3},
                            {4, 2, 5},
                            {7, 8, 6}
                    });
                    break;
                case 1:
                    expected = new Board(new int[][]{
                            {1, 0, 3},
                            {4, 2, 5},
                            {7, 8, 6}
                    });
                    break;
                case 2:
                    expected = new Board(new int[][]{
                            {1, 2, 3},
                            {4, 0, 5},
                            {7, 8, 6}
                    });
                    break;
                case 3:
                    expected = new Board(new int[][]{
                            {1, 2, 3},
                            {4, 5, 0},
                            {7, 8, 6}
                    });
                    break;
                case 4:
                    expected = new Board(new int[][]{
                            {1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 0}
                    });
                    break;
                default:
                    // Should not reach
                    assertTrue(false, "Solution should have only 5 moves");
                    throw new IllegalStateException("Solution should have only 5 moves");
            }
            assertEquals(expected, solution);
            move++;
        }
    }

    @Test
    void testWhenThereIsSolution2x2() {
        assertTimeout(Duration.ofMinutes(1), () -> {
            Solver solver = new Solver(new Board(new int[][]{
                    {1, 0},
                    {3, 2}
            }));
            assertTrue(solver.isSolvable());
            assertEquals(2, solver.moves());
            assertNotNull(solver.solution());
        });
    }

    @Test
    void testWhenThereIsNoSolution3x3() {
        assertTimeout(Duration.ofMinutes(1), () -> {
            Solver solver = new Solver(new Board(new int[][]{
                    {1, 2, 3},
                    {4, 5, 6},
                    {8, 7, 0}
            }));
            assertFalse(solver.isSolvable());
            assertEquals(-1, solver.moves());
            assertNull(solver.solution());
        });
    }
    
// MAY TAKE LONG TIME
//    @Test
//    void testWhenThereIsNoSolution4x4() {
//        assertTimeout(Duration.ofMinutes(1), () -> {
//            Solver solver = new Solver(new Board(new int[][]{
//                    {1, 2, 3, 4},
//                    {5, 6, 7, 8},
//                    {9, 10, 11, 12},
//                    {13, 15, 14, 0}
//            }));
//            assertFalse(solver.isSolvable());
//            assertEquals(-1, solver.moves());
//            assertNull(solver.solution());
//        });
//    }
}
