package com.pavelhudau.queues;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestRandomizedQueue {
    @Test
    void testIsEmptyWhenRandomizedQueueIsEmpty() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assertTrue(randQueue.isEmpty());

        randQueue.enqueue(2);
        randQueue.dequeue();
        randQueue.enqueue(1);
        randQueue.dequeue();

        assertTrue(randQueue.isEmpty());
    }

    @Test
    void testIsEmptyWhenDequeIsNotEmpty() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        randQueue.enqueue(1);
        assertFalse(randQueue.isEmpty());

        randQueue.enqueue(2);
        randQueue.dequeue();
        assertFalse(randQueue.isEmpty());
    }

    @Test
    void testSize() {
        int size = 111;
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assertEquals(0, randQueue.size());

        for (int i = 0; i < size; i++) {
            randQueue.enqueue(i);
            assertEquals(i + 1, randQueue.size());
        }

        for (int i = 0; i < size; i++) {
            randQueue.dequeue();
            assertEquals(size - (i + 1), randQueue.size());
        }

        assertEquals(0, randQueue.size());
    }

    @Test
    void testSample() {
        int size = 10;
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        for (int i = 0; i < size; i++) {
            randQueue.enqueue(i);
        }

        for (int i = 0; i < size; i++) {
            assertNotNull(randQueue.sample());
            assertEquals(size, randQueue.size());
        }
    }


    @Test
    void testEnqueueWhenItemIsNullThenExceptionIsThrown() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assertThrows(IllegalArgumentException.class, () -> randQueue.enqueue(null));
    }

    @Test
    void testDequeueWhenIsEmptyThenExceptionIsThrown() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assertThrows(NoSuchElementException.class, randQueue::dequeue);
    }

    @Test
    void testIteratorWithForLoop() {
        int size = 11;
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        for (int i = 0; i < size; i++) {
            randQueue.enqueue(i);
        }

        int iteration = 0;
        for (Integer item : randQueue) {
            assertNotNull(item);
            iteration++;
        }
        assertEquals(size, iteration);
    }

    @Test
    void testIteratorWithForLoopWhenEmpty() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        for (Integer item : randQueue) {
            assertNotNull(item);
        }
    }

    @Test
    void testIteratorWhenEmptyThenHasNextReturnsFalse() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assertFalse(randQueue.iterator().hasNext());
    }

    @Test
    void testIteratorWhenEmptyThenNextThrowsException() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assertThrows(NoSuchElementException.class, () -> randQueue.iterator().next());
    }
}
