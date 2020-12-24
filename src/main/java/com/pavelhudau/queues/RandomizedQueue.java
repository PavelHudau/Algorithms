package com.pavelhudau.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int EXPAND_RATIO = 2;
    private static final int SHRINK_RATIO = EXPAND_RATIO * 2;

    private Object[] items;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = new Object[0];
    }

    private RandomizedQueue(int capacity) {
        items = new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null");
        }

        if (this.size == this.items.length) {
            this.expand();
        }

        this.items[this.size] = item;
        this.size ++;
        this.randomSwap(this.size-1);
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }

        Item item = (Item) this.items[this.size - 1];
        this.items[this.size - 1] = null;
        this.size --;

        if (this.size <= this.items.length / SHRINK_RATIO) {
            this.shrink();
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.size == 0) {
            throw new NoSuchElementException("RandomizedQueue is empty");
        }
        return (Item) this.items[StdRandom.uniform(this.size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(this);
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    private void randomSwap(int indexToSwap) {
        int randPosition = StdRandom.uniform(this.size);
        Object swappingItem = this.items[indexToSwap];
        this.items[indexToSwap] = this.items[randPosition];
        this.items[randPosition] = swappingItem;
    }

    private void expand() {
        int newCapacity = this.size > 0
                ? this.size * EXPAND_RATIO
                : EXPAND_RATIO;
        Object[] newItems = new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            newItems[i] = this.items[i];
        }
        this.items = newItems;
    }

    private void shrink() {
        int newCapacity = this.size;
        Object[] newItems = new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            newItems[i] = this.items[i];
        }
        this.items = newItems;
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private RandomizedQueue<Item> copy;
        public RandomizedQueueIterator(RandomizedQueue<Item> randQueue) {
            copy = new RandomizedQueue<Item>(randQueue.size);
            for (int i = 0; i < randQueue.size; i++) {
                copy.enqueue((Item) randQueue.items[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Item next() {
            return copy.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove is not supported");
        }
    }
}
