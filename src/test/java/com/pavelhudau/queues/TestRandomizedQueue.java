package com.pavelhudau.queues;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRandomizedQueue {
    @Test
    void testIsEmptyWhenRandomizedQueueIsEmpty() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assert randQueue.isEmpty();

        randQueue.enqueue(2);
        randQueue.dequeue();
        randQueue.enqueue(1);
        randQueue.dequeue();

        assert randQueue.isEmpty();
    }

    @Test
    void testIsEmptyWhenDequeIsNotEmpty() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        randQueue.enqueue(1);
        assert !randQueue.isEmpty();

        randQueue.enqueue(2);
        randQueue.dequeue();
        assert !randQueue.isEmpty();
    }

    @Test
    void testSize() {
        int size = 111;
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assert randQueue.size() == 0;

        for (int i = 0; i < size; i++) {
            randQueue.enqueue(i);
            assert randQueue.size() == i + 1;
        }

        for (int i = 0; i < size; i++) {
            randQueue.dequeue();
            assert randQueue.size() == size - (i + 1);
        }

        assert randQueue.size() == 0;
    }

    @Test
    void testSample() {
        int size = 10;
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        for (int i = 0; i < size; i++) {
            randQueue.enqueue(i);
        }

        for (int i = 0; i < size; i++) {
            assert randQueue.sample() != null;
            assert randQueue.size() == size;
        }
    }


    @Test
    void testEnqueueWhenItemIsNullThenExceptionIsThrown() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assertThrows(IllegalArgumentException.class, () -> {
            randQueue.enqueue(null);
        });
    }

    @Test
    void testDequeueWhenIsEmptyThenExceptionIsThrown() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assertThrows(NoSuchElementException.class, () -> {
            randQueue.dequeue();
        });
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
            assert item != null;
            iteration ++;
        }
        assert iteration == size;
    }

    @Test
    void testIteratorWithForLoopWhenEmpty() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        for (Integer item : randQueue) {
            assert item != null;
        }
    }

    @Test
    void testIteratorWhenEmptyThenHasNextReturnsFalse() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assert !randQueue.iterator().hasNext();
    }

    @Test
    void testIteratorWhenEmptyThenNextThrowsException() {
        RandomizedQueue<Integer> randQueue = new RandomizedQueue<>();
        assertThrows(NoSuchElementException.class, () -> {
            randQueue.iterator().next();
        });
    }
}
