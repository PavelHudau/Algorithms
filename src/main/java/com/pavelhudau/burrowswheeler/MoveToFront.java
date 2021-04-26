package com.pavelhudau.burrowswheeler;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private final char[] chars = new char[256];

    public MoveToFront() {
        for (int i = 0; i < this.chars.length; i++) {
            this.chars[i] = (char) i;
        }
    }

    /**
     * Apply move-to-front encoding, reading from standard input and writing to standard output.
     */
    public static void encode() {
        MoveToFront mtf = new MoveToFront();
        while (!BinaryStdIn.isEmpty()) {
            char ch = BinaryStdIn.readChar();
            BinaryStdOut.write(mtf.encodeChar(ch));
        }
    }

    /**
     * Apply move-to-front decoding, reading from standard input and writing to standard output.
     */
    public static void decode() {
        MoveToFront mtf = new MoveToFront();
        while (!BinaryStdIn.isEmpty()) {
            char chPosition = BinaryStdIn.readChar();
            BinaryStdOut.write(mtf.decodeChar(chPosition));
        }
    }

    char encodeChar(char ch) {
        int chPosition = this.findPosition(ch);
        this.moveToFront(chPosition);
        return (char) chPosition;
    }

    char decodeChar(char charPosition) {
        char ch = this.chars[charPosition];
        this.moveToFront(charPosition);
        return ch;
    }

    private int findPosition(char ch) {
        for (int i = 0; i < this.chars.length; i++) {
            if (this.chars[i] == ch) {
                return i;
            }
        }

        return -1;
    }

    private void moveToFront(int position) {
        char chAtPosition = this.chars[position];
        for (int i = position; i > 0; i--) {
            this.chars[i] = this.chars[i - 1];
        }

        this.chars[0] = chAtPosition;
    }

    /**
     * Runs Move to From encoding and decoding.
     *
     * @param args Ff args[0] is "-", apply move-to-front encoding,
     *             else if args[0] is "+", apply move-to-front decoding.
     */
    public static void main(String[] args) {
        switch (args[0]) {
            case "-":
                encode();
                break;
            case "+":
                decode();
                break;
            default:
                throw new IllegalArgumentException("Argument must be '-' or '+'");
        }
    }
}
