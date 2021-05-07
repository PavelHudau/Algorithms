package com.pavelhudau.burrowswheeler;

public class CircularSuffixArray {
    private final int strLength;
    private final int[] indices;

    /**
     * Builds and sorts circular suffix array of s.
     *
     * @param s A string for which circular array will be created.
     */
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s can not be null.");
        }

        ThreeWayStringQuickSort sorter = new ThreeWayStringQuickSort(s);
        this.strLength = sorter.strLength;
        this.indices = sorter.indices;
    }

    /**
     * Length of the string for which circular array was built.
     *
     * @return Length of the string for which circular array was built.
     */
    public int length() {
        return this.strLength;
    }

    /**
     * Index of i-th sorted suffix.
     *
     * @param i index in sorted array of suffixes.
     * @return Index of i-th sorted suffix.
     */
    public int index(int i) {
        if (i < 0 || i >= this.strLength) {
            throw new IllegalArgumentException("i must be withing length of the string for which circular array was built.");
        }

        return this.indices[i];
    }

    private static class ThreeWayStringQuickSort {
        private final int strLength;
        private final int[] indices;
        private final String[] suffixes;

        private ThreeWayStringQuickSort(String s) {
            this.strLength = s.length();
            this.suffixes = new String[this.strLength];
            this.indices = new int[this.strLength];

            this.initializeSuffixesAndIndices(s);
            this.sort();
        }

        private void initializeSuffixesAndIndices(String s) {
            for (int i = 0; i < this.strLength; i++) {
                this.suffixes[i] = s.substring(i, this.strLength) + s.substring(0, i);
                this.indices[i] = i;
            }
        }

        private void sort() {
            this.threeWayStringQuickSort(0, this.strLength - 1, 0);
        }

        private void threeWayStringQuickSort(int lo, int hi, int sortPosition) {
            if (lo >= hi || sortPosition >= this.strLength) {
                return;
            }

            int midLo = lo;
            int midHi = hi;
            char partitionChar = this.suffixes[lo].charAt(sortPosition);
            int i = lo + 1;

            while (i <= midHi) {
                char charToCompareBy = this.suffixes[i].charAt(sortPosition);
                if (charToCompareBy < partitionChar) {
                    this.exchange(midLo, i);
                    midLo++;
                    i++;
                } else if (charToCompareBy > partitionChar) {
                    this.exchange(midHi, i);
                    midHi--;
                } else {
                    i++;
                }
            }

            this.threeWayStringQuickSort(lo, midLo - 1, sortPosition);
            this.threeWayStringQuickSort(midLo, midHi, sortPosition + 1);
            this.threeWayStringQuickSort(midHi + 1, hi, sortPosition);
        }

        private void exchange(int first, int second) {
            String tmpSuffixOne = this.suffixes[first];
            this.suffixes[first] = this.suffixes[second];
            this.suffixes[second] = tmpSuffixOne;

            int tmpIdxOne = this.indices[first];
            this.indices[first] = this.indices[second];
            this.indices[second] = tmpIdxOne;
        }
    }
}
