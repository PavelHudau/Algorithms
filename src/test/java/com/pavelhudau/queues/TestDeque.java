package com.pavelhudau.queues;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDeque {
    @Test
    void testIsEmptyWhenDequeIsEmpty() {
        Deque<Integer> deque = new Deque<>();
        assertTrue(deque.isEmpty());

        deque.addFirst(2);
        deque.removeFirst();

        assertTrue(deque.isEmpty());
    }

    @Test
    void testIsEmptyWhenDequeIsNotEmpty() {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        assertFalse(deque.isEmpty());

        deque.addLast(2);
        deque.removeFirst();
        assertFalse(deque.isEmpty());
    }

    @Test
    void testSize() {
        Deque<Integer> deque = new Deque<>();
        assertEquals(0, deque.size());

        deque.addFirst(1);
        assertEquals(1, deque.size());

        deque.addLast(2);
        assertEquals(2, deque.size());

        deque.removeFirst();
        assertEquals(1, deque.size());

        deque.removeLast();
        assertEquals(0, deque.size());
    }

    @Test
    void testAddFirst() {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        assertEquals(1, deque.removeFirst());
        assertEquals(2, deque.removeFirst());
        assertEquals(3, deque.removeFirst());
    }

    @Test
    void testAddLast() {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(3);
        deque.addLast(2);
        deque.addLast(1);
        assertEquals(1, deque.removeLast());
        assertEquals(2, deque.removeLast());
        assertEquals(3, deque.removeLast());
    }

    @Test
    void testRemoveLast() {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addFirst(2);
        deque.addFirst(1);
        assertEquals(5, deque.removeLast());
        assertEquals(4, deque.removeLast());
        assertEquals(3, deque.removeLast());
        assertEquals(2, deque.removeLast());
        assertEquals(1, deque.removeLast());
    }

    @Test
    void testRemoveFirst() {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addFirst(2);
        deque.addFirst(1);
        assertEquals(1, deque.removeFirst());
        assertEquals(2, deque.removeFirst());
        assertEquals(3, deque.removeFirst());
        assertEquals(4, deque.removeFirst());
        assertEquals(5, deque.removeFirst());
    }

    @Test
    void testAddFirstWhenItemIsNullThenExceptionIsThrown() {
        Deque<Integer> deque = new Deque<>();
        assertThrows(IllegalArgumentException.class, () -> {
            deque.addFirst(null);
        });
    }

    @Test
    void testAddLastWhenItemIsNullThenExceptionIsThrown() {
        Deque<Integer> deque = new Deque<>();
        assertThrows(IllegalArgumentException.class, () -> {
            deque.addLast(null);
        });
    }

    @Test
    void testRemoveFirstWhenDequeIsEmptyThenExceptionIsThrown() {
        Deque<Integer> deque = new Deque<>();
        assertThrows(NoSuchElementException.class, () -> {
            deque.removeFirst();
        });
    }

    @Test
    void testRemoveLastWhenDequeIsEmptyThenExceptionIsThrown() {
        Deque<Integer> deque = new Deque<>();
        assertThrows(NoSuchElementException.class, () -> {
            deque.removeLast();
        });
    }

    @Test
    void testIteratorWithForLoop() {
        Deque<Integer> deque = new Deque<>();
        int size = 11;
        for (int i = 0; i < size; i++) {
            deque.addLast(i);
        }

        int iterations = 0;
        for (Integer item : deque) {
            assertEquals(iterations, item);
            iterations++;
        }

        assertEquals(iterations, size);
    }

    @Test
    void testIteratorWhenEmptyThenHasNextReturnsFalse() {
        Deque<Integer> deque = new Deque<>();
        assertFalse(deque.iterator().hasNext());
    }

    @Test
    void testIteratorWhenEmptyThenNextThrowsException() {
        Deque<Integer> deque = new Deque<>();
        assertThrows(NoSuchElementException.class, () -> {
            deque.iterator().next();
        });
    }
}
