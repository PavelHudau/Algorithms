package com.pavelhudau.burrowswheeler;

import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdIn;

public class BurrowsWheeler {
    /**
     * Apply Burrows-Wheeler transform,
     * reading from standard input and writing to standard output.
     */
    public static void transform() {
        BurrowsWheeler bw = new BurrowsWheeler();
        TransformResult result = bw.transform(StdIn.readAll());
        BinaryStdOut.write(result.originalStrIndex);
        for (char ch: result.transformedChars) {
            BinaryStdOut.write(ch);
        }
    }

    /**
     * Apply Burrows-Wheeler inverse transform,
     * reading from standard input and writing to standard output.
     */
    public static void inverseTransform() {

    }

    TransformResult transform(String input) {
        CircularSuffixArray csa = new CircularSuffixArray(input);
        char[] transformedChars = new char[csa.length()];
        int originalStrIdx = -1;

        for (int i = 0; i < transformedChars.length; i++) {
            int originalIndex = csa.index(i);
            if(originalIndex == 0) {
                originalStrIdx = i;
                transformedChars[i] = input.charAt(input.length() - 1);
            }
            else {
                transformedChars[i] = input.charAt(csa.index(i) - 1);
            }
        }

        return new TransformResult(originalStrIdx, transformedChars);
    }

    /**
     * Apply Burrows-Wheeler transformation or inverse transformation.
     * If args[0] is "-", apply Burrows-Wheeler transform.
     * Ff args[0] is "+", apply Burrows-Wheeler inverse transform
     *
     * @param args args[0] defines whether transformation is inverse or not.
     */
    public static void main(String[] args) {
        switch (args[0]) {
            case "-":
                inverseTransform();
                break;
            case "+":
                transform();
                break;
            default:
                throw new IllegalArgumentException("Argument must be '-' or '+'");
        }
    }

    class TransformResult {
        final int originalStrIndex;
        final char[] transformedChars;

        TransformResult(int originalStrIndex, char[] transformedChars) {
            this.originalStrIndex = originalStrIndex;
            this.transformedChars = transformedChars;
        }
    }
}
