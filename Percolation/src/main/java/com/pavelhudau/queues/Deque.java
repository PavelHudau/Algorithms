package com.pavelhudau.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue
 * that supports adding and removing items from either the front or the back of the data structure.
 */
public class Deque<Item> implements Iterable<Item> {
    private AnItem<Item> front;
    private AnItem<Item> back;
    private int length = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.length == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can not be null");
        }

        AnItem<Item> anItem = new AnItem<>(item, this.front, null);
        if (this.front != null) {
            this.front.previous = anItem;
        }
        if (this.back == null) {
            // Adding first item
            this.back = anItem;
        }

        this.front = anItem;
        this.length ++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can not be null");
        }

        AnItem<Item> anItem = new AnItem<>(item, null, this.back);
        if (this.back != null) {
            this.back.next = anItem;
        }
        if (this.front == null) {
            // Adding first item
            this.front = anItem;
        }

        this.back = anItem;
        this.length ++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.length == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        AnItem<Item> oldFront = this.front;
        this.front = this.front.next;
        this.length --;

        if (this.front == null) {
            // Removed last item;
            this.back = null;
        }
        else {
            this.front.previous = null;
        }

        return oldFront.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.length == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        AnItem<Item> oldBack = this.back;
        this.back = this.back.previous;
        this.length --;

        if (this.back == null) {
            // Removed last item;
            this.front = null;
        }
        else {
            this.back.next = null;
        }

        return oldBack.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator<>(this);
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    private static class AnItem<Item> {
        private final Item item;
        private AnItem<Item> next;
        private AnItem<Item> previous;

        private AnItem(Item item, AnItem<Item> next, AnItem<Item> previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private static class DequeIterator<Item> implements Iterator<Item> {
        private AnItem<Item> current;

        private DequeIterator(Deque<Item> deque) {
            this.current = deque.front;
        }

        @Override
        public boolean hasNext() {
            return  this.current != null && this.current.next != null;
        }

        @Override
        public Item next() {
            if (this.current == null || this.current.next == null) {
                throw new NoSuchElementException("Deque is empty");
            }

            AnItem<Item> oldCurrent = this.current;
            this.current = this.current.next;
            return oldCurrent.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }
    }
}
