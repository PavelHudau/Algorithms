package com.pavelhudau.burrowswheeler;

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.MinPQ;

public class BurrowsWheeler {
    /**
     * Apply Burrows-Wheeler transform,
     * reading from standard input and writing to standard output.
     */
    public static void transform() {
        BurrowsWheeler bw = new BurrowsWheeler();
        TransformResult result = bw.transformMe(BinaryStdIn.readString());
        BinaryStdOut.write(result.originalStrIndex);
        for (char ch : result.transformedChars) {
            BinaryStdOut.write(ch);
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    /**
     * Apply Burrows-Wheeler inverse transform,
     * reading from standard input and writing to standard output.
     */
    public static void inverseTransform() {
        BurrowsWheeler bw = new BurrowsWheeler();
        int originalStrIndex = BinaryStdIn.readInt();
        char[] result = bw.inverseTransformMe(originalStrIndex, BinaryStdIn.readString().toCharArray());
        for (char ch : result) {
            BinaryStdOut.write(ch);
        }

        BinaryStdIn.close();
        BinaryStdOut.close();
    }

    private TransformResult transformMe(String input) {
        CircularSuffixArray csa = new CircularSuffixArray(input);
        char[] transformedChars = new char[csa.length()];
        int originalStrIdx = -1;

        for (int i = 0; i < transformedChars.length; i++) {
            int originalIndex = csa.index(i);
            if (originalIndex == 0) {
                originalStrIdx = i;
                transformedChars[i] = input.charAt(input.length() - 1);
            } else {
                transformedChars[i] = input.charAt(csa.index(i) - 1);
            }
        }

        return new TransformResult(originalStrIdx, transformedChars);
    }

    private char[] inverseTransformMe(int originalStrIndex, char[] transformedChars) {
        int[] nextArray = constructNextArray(transformedChars);
        char[] inverseTransformed = new char[transformedChars.length];
        int next = originalStrIndex;
        for (int i = 0; i < inverseTransformed.length; i++) {
            inverseTransformed[i] = transformedChars[nextArray[next]];
            next = nextArray[next];
        }

        return inverseTransformed;
    }

    private static char[] reconstructFirstSuffixesColumn(char[] encoded) {
        Character[] sorted = new Character[encoded.length];
        for (int i = 0; i < encoded.length; i++) {
            sorted[i] = encoded[i];
        }

        Quick.sort(sorted);
        char[] firstColInSuffixes = new char[sorted.length];
        for (int i = 0; i < sorted.length; i++) {
            firstColInSuffixes[i] = sorted[i];
        }

        return firstColInSuffixes;
    }

    private static int[] constructNextArray(char[] transformedChars) {
        char[] firstSuffixesCol = reconstructFirstSuffixesColumn(transformedChars);
        ST<Character, MinPQ<Integer>> transformedCharsPositionMap = createCharPositionMap(transformedChars);
        int[] nextArray = new int[firstSuffixesCol.length];
        for (int i = 0; i < firstSuffixesCol.length; i++) {
            char firstColChar = firstSuffixesCol[i];
            MinPQ<Integer> suffixPos = transformedCharsPositionMap.get(firstColChar);
            nextArray[i] = suffixPos.delMin();
        }
        return nextArray;
    }

    private static ST<Character, MinPQ<Integer>> createCharPositionMap(char[] chars) {
        ST<Character, MinPQ<Integer>> map = new ST<>();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (!map.contains(chars[i])) {
                map.put(ch, new MinPQ<>());
            }
            map.get(ch).insert(i);
        }

        return map;
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

    private static class TransformResult {
        final int originalStrIndex;
        final char[] transformedChars;

        TransformResult(int originalStrIndex, char[] transformedChars) {
            this.originalStrIndex = originalStrIndex;
            this.transformedChars = transformedChars;
        }
    }
}
