package com.pavelhudau.points;

public class BruteCollinearPoints {
    private static final int EXPAND_RATIO = 2;

    private int numberOfSegments;
    private LineSegment[] segments;

    /**
     * Finds all line segments containing 4 points.
     * <p>
     * * @param points an array of points that contains max 4 points in a segment.
     **/
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points can not be null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("No point can not be null");
            }
        }

        this.numberOfSegments = 0;
        this.segments = new LineSegment[0];

        for (int i1 = 0; i1 < points.length; i1++) {
            for (int i2 = i1 + 1; i2 < points.length; i2++) {
                double slope12 = calculateSlope(points[i1], points[i2]);
                for (int i3 = i2 + 1; i3 < points.length; i3++) {
                    double slope13 = calculateSlope(points[i1], points[i3]);
                    if (slope12 != slope13) {
                        // third point is not part of the slope,
                        // there is no need to continue searching for the 4-th pint.
                        continue;
                    }

                    for (int i4 = i3 + 1; i4 < points.length; i4++) {
                        double slope14 = calculateSlope(points[i1], points[i4]);
                        if (slope12 == slope14) {
                            // Segment found
                            this.addSegment(createSegment(new Point[]{
                                    points[i1], points[i2], points[i3], points[i4]
                            }));
                            break;
                        }
                    }
                }
            }
        }

        this.shrinkSegmentsToRealSize();
    }

    /**
     * The number of line segments.
     **/
    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    /**
     * The line segments.
     **/
    public LineSegment[] segments() {
        LineSegment[] segmentsCopy = new LineSegment[this.segments.length];
        System.arraycopy(this.segments, 0, segmentsCopy, 0, this.segments.length);
        return segmentsCopy;
    }

    private static double calculateSlope(Point p1, Point p2) {
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Point can not be null");
        }
        if (p1.compareTo(p2) == 0) {
            throw new IllegalArgumentException("Duplicate points " + p1.toString());
        }
        return p1.slopeTo(p2);
    }

    private static LineSegment createSegment(Point[] points) {
        Point min = points[0];
        Point max = points[0];
        for (int i = 1; i < points.length; i++) {
            if (min.compareTo(points[i]) > 0) {
                min = points[i];
            }
            if (max.compareTo(points[i]) < 0) {
                max = points[i];
            }
        }
        return new LineSegment(min, max);
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
}
