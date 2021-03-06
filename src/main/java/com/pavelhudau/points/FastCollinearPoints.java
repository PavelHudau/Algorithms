package com.pavelhudau.points;

import java.util.Comparator;
import java.util.LinkedList;

public class FastCollinearPoints {
    private int numberOfSegments;
    private final LinkedList<LineSegment> segments;

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
        this.segments = new LinkedList<>();


        for (Point basisPoint : pointsByPosition) {
            this.sortWithComparator(points, basisPoint.slopeOrder());

            // We skip first point because it is basisPoint itself.
            // basisPoint.slopeTo(basisPoint) will return Double.NEGATIVE_INFINITY
            // which is the smallest double, therefore it will always be first in
            // a sorted by slope array.
            int backRunnerIdx = 1;
            while (backRunnerIdx < points.length) {
                double backRunnerSlope = calculateSlope(basisPoint, points[backRunnerIdx]);
                boolean hasSeenSegment = hasSeenSegment(basisPoint, points[backRunnerIdx]);
                int frontRunnerIdx = backRunnerIdx + 1;
                while (frontRunnerIdx < points.length) {
                    double frontRunnerSlope = calculateSlope(basisPoint, points[frontRunnerIdx]);
                    if (Double.compare(frontRunnerSlope, backRunnerSlope) == 0) {
                        hasSeenSegment = hasSeenSegment || hasSeenSegment(basisPoint, points[frontRunnerIdx]);
                        frontRunnerIdx++;
                    } else {
                        break;
                    }
                }

                if (!hasSeenSegment && frontRunnerIdx - backRunnerIdx >= 3) {
                    addSegment(createSegment(points, basisPoint, backRunnerIdx, frontRunnerIdx));
                }

                backRunnerIdx = frontRunnerIdx;
            }
        }
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
        LineSegment[] segmentsCopy = new LineSegment[this.numberOfSegments];
        int i = 0;
        for (LineSegment segment : this.segments) {
            segmentsCopy[i] = segment;
            i++;
        }
        return segmentsCopy;
    }

    private static double calculateSlope(Point p1, Point p2) {
        double slope = p1.slopeTo(p2);
        if (Double.compare(slope, Double.NEGATIVE_INFINITY) == 0) {
            throw new IllegalArgumentException("Duplicate points " + p1.toString());
        }

        return p1.slopeTo(p2);
    }

    private static boolean hasSeenSegment(Point basisPoint, Point pointOnASegment) {
        // We select smallest points first as basis, therefore if point forms a segment
        // that has never seen before, then it will be bigger than a basis point.
        // If point on a segment is less than the basis point, then the segment was already
        // counted and current basis is just a point on the segment.
        return pointOnASegment.compareTo(basisPoint) < 0;
    }

    private static LineSegment createSegment(Point[] points, Point basisPoint, int backRunnerIdx, int frontRunnerIdx) {
        Point max = basisPoint;
        for (int i = backRunnerIdx; i < frontRunnerIdx; i++) {
            if (max.compareTo(points[i]) < 0) {
                max = points[i];
            }
        }
        return new LineSegment(basisPoint, max);
    }

    private void addSegment(LineSegment segment) {
        this.segments.add(segment);
        this.numberOfSegments++;
    }

    private void sortPointsByPosition(Point[] a) {
        this.sortWithComparator(a, null);
    }

    private void sortWithComparator(Point[] a, Comparator<Point> comparator) {
        Point[] aux = new Point[a.length];
        System.arraycopy(a, 0, aux, 0, a.length);
        this.sort(a, aux, 0, a.length - 1, comparator);
        assert isSorted(a, comparator);
    }

    private void sort(Point[] a, Point[] aux, int lo, int hi, Comparator<Point> comparator) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, comparator);
        sort(a, aux, mid + 1, hi, comparator);
        merge(a, aux, lo, mid, hi, comparator);
    }

    private void merge(Point[] a, Point[] aux, int lo, int mid, int hi, Comparator<Point> comparator) {
        assert isSorted(a, lo, mid, comparator);
        assert isSorted(a, mid + 1, hi, comparator);

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int left = lo;
        int right = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (left > mid) {
                // Exhausted left half
                a[k] = aux[right];
                right++;
            } else if (right > hi) {
                // Exhausted right half
                a[k] = aux[left];
                left++;
            } else if (less(aux[left], aux[right], comparator)) {
                // Left half item is smaller than right
                a[k] = aux[left];
                left++;
            } else {
                // Right half item is smaller than left
                a[k] = aux[right];
                right++;
            }
        }
    }

    private static boolean less(Point a, Point b, Comparator<Point> comparator) {
        if (comparator == null) {
            return a.compareTo(b) < 0;
        } else {
            return comparator.compare(a, b) < 0;
        }
    }

    private static boolean isSorted(Point[] a, Comparator<Point> comparator) {
        return isSorted(a, 0, a.length - 1, comparator);
    }

    private static boolean isSorted(Point[] a, int lo, int hi, Comparator<Point> comparator) {
        for (int i = lo + 1; i <= hi; ++i) {
            if (less(a[i], a[i - 1], comparator)) {
                return false;
            }
        }

        return true;
    }
}