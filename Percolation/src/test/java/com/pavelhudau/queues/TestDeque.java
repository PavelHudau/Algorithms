package com.pavelhudau.queues;

import org.junit.jupiter.api.Test;

public class TestDeque {
    @Test
    void testIsEmptyWhenDequeIsEmpty() {
        Deque<Integer> deque = new Deque<>();
        assert deque.isEmpty();

        deque.addFirst(2);
        deque.removeFirst();

        assert deque.isEmpty();
    }

    @Test
    void testIsEmptyWhenDequeIsNotEmpty() {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        assert !deque.isEmpty();

        deque.addLast(2);
        deque.removeFirst();
        assert !deque.isEmpty();
    }

    @Test
    void testSize() {
        Deque<Integer> deque = new Deque<>();
        assert deque.size() == 0;

        deque.addFirst(1);
        assert deque.size() == 1;

        deque.addLast(2);
        assert deque.size() == 2;

        deque.removeFirst();
        assert deque.size() == 1;

        deque.removeLast();
        assert deque.size() == 0;
    }

    @Test
    void testAddFirst() {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        assert deque.removeFirst() == 1;
        assert deque.removeFirst() == 2;
        assert deque.removeFirst() == 3;
    }

    @Test
    void testAddLast() {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(3);
        deque.addLast(2);
        deque.addLast(1);
        assert deque.removeLast() == 1;
        assert deque.removeLast() == 2;
        assert deque.removeLast() == 3;
    }

    @Test
    void testRemoveLast() {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addFirst(2);
        deque.addFirst(1);
        assert deque.removeLast() == 5;
        assert deque.removeLast() == 4;
        assert deque.removeLast() == 3;
        assert deque.removeLast() == 2;
        assert deque.removeLast() == 1;
    }

    @Test
    void testRemoveFirst() {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addFirst(2);
        deque.addFirst(1);
        assert deque.removeFirst() == 1;
        assert deque.removeFirst() == 2;
        assert deque.removeFirst() == 3;
        assert deque.removeFirst() == 4;
        assert deque.removeFirst() == 5;
    }
}
