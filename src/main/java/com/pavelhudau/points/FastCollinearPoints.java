package com.pavelhudau.points;

import edu.princeton.cs.algs4.Merge;

import java.util.Comparator;

public class FastCollinearPoints {
    private static final int EXPAND_RATIO = 2;

    private int numberOfSegments;
    private LineSegment[] segments;

    /**
     * Finds all line segments containing 4 or more points
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points can not be null");
        }

        Point[] pointsByPosition = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("No point can not be null");
            }
            pointsByPosition[i] = points[i];
        }

        // Sort points by position
        sortPointsByPosition(pointsByPosition);

        this.numberOfSegments = 0;
        this.segments = new LineSegment[0];


        for (Point basisPoint : pointsByPosition) {
            this.sortWithComparator(points, basisPoint.slopeOrder());

            // We skip first point because it is basisPoint itself.
            // basisPoint.slopeTo(basisPoint) will return Double.NEGATIVE_INFINITY
            // which is the smallest double, therefore it will always be first in
            // a sorted by slope array.
            int backRunnerIdx = 1;
            while (backRunnerIdx < points.length) {
                double backRunnerSlope = calculateSlope(basisPoint, points[backRunnerIdx]);
                int frontRunnerIdx = backRunnerIdx + 1;
                while (frontRunnerIdx < points.length) {
                    double frontRunnerSlope = calculateSlope(basisPoint, points[frontRunnerIdx]);
                    if (Double.compare(frontRunnerSlope, backRunnerSlope) == 0) {
                        frontRunnerIdx++;
                    } else {
                        break;
                    }
                }

                if (frontRunnerIdx - backRunnerIdx >= 3) {
                    addSegment(new LineSegment(basisPoint, points[frontRunnerIdx - 1]));
                }

                backRunnerIdx = frontRunnerIdx;
            }
        }

        this.shrinkSegmentsToRealSize();
    }

    /**
     * The number of line segments
     *
     * @return number of segments
     */
    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    /**
     * The line segments
     *
     * @return List of segments
     */
    public LineSegment[] segments() {
        return this.segments;
    }

    private static double calculateSlope(Point p1, Point p2) {
        double slope = p1.slopeTo(p2);
        if (Double.compare(slope, Double.NEGATIVE_INFINITY) == 0) {
            throw new IllegalArgumentException("Duplicate points " + p1.toString());
        }
        return p1.slopeTo(p2);
    }

    private void addSegment(LineSegment segment) {
        if (this.segments.length >= this.numberOfSegments) {
            this.expandSegments();
        }
        this.segments[this.numberOfSegments] = segment;
        this.numberOfSegments++;
    }

    private void expandSegments() {
        int newCapacity = this.segments.length > 0
                ? this.segments.length * EXPAND_RATIO
                : EXPAND_RATIO;
        LineSegment[] newItems = new LineSegment[newCapacity];
        System.arraycopy(this.segments, 0, newItems, 0, this.segments.length);
        this.segments = newItems;
    }

    private void shrinkSegmentsToRealSize() {
        int newCapacity = this.numberOfSegments;
        LineSegment[] newItems = new LineSegment[newCapacity];
        System.arraycopy(this.segments, 0, newItems, 0, newCapacity);
        this.segments = newItems;
    }

    private void sortPointsByPosition(Point[] a) {
        this.sortWithComparator(a, null);
    }

    private void sortWithComparator(Point[] a, Comparator<Point> comparator) {
        Point[] aux = new Point[a.length];
        System.arraycopy(a, 0, aux, 0, a.length);
        this.sort(a, aux, 0, a.length, comparator);
    }

    private void sort(Point[] a, Point[] aux, int lo, int hi, Comparator<Point> comparator) {
        if (lo > hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, comparator);
        sort(a, aux, mid + 1, hi, comparator);
        merge(a, aux, lo, mid, hi, comparator);
    }

    private void merge(Point[] a, Point[] aux, int lo, int mid, int hi, Comparator<Point> comparator) {
        int left = lo;
        int right = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (left > mid) {
                // Exhausted left
                a[k] = aux[right];
                right++;
            } else if (right > hi) {
                // Exhausted right
                a[k] = aux[left];
                left++;
            } else if (less(aux[left], aux[right], comparator)) {
                // Left is smaller than right
                a[k] = aux[left];
                left++;
            } else {
                // Right is smaller than left
                a[k] = aux[right];
                right++;
            }
        }
    }

    private boolean less(Point a, Point b, Comparator<Point> comparator) {
        if (comparator == null) {
            return a.compareTo(b) < 0;
        }
        else {
            return comparator.compare(a, b) < 0;
        }
    }
}