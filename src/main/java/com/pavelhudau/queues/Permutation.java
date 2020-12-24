package com.pavelhudau.queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rangQ = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            rangQ.enqueue(input);
        }

        while (k > 0 && !rangQ.isEmpty()) {
            StdOut.println(rangQ.dequeue());
            k--;
        }
    }
}
