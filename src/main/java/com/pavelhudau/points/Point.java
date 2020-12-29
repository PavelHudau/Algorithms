package com.pavelhudau.points;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        throw new IllegalStateException("Not implemented");
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        throw new IllegalStateException("Not implemented");
    }

    // string representation
    public String toString() {
        throw new IllegalStateException("Not implemented");
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y > that.y) {
            return 1;
        } else if (this.y < that.y) {
            return -1;
        } else if (this.x > that.x) {
            return 1;
        } else if (this.x < that.x) {
            return -1;
        }
        return 0;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        double xDiff = that.x - this.x;
        double yDiff = that.y - this.y;
        if (xDiff == 0 && yDiff == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (xDiff == 0) {
            return Double.POSITIVE_INFINITY;
        } else if (yDiff == 0) {
            return 0;
        } else {
            return yDiff / xDiff;
        }
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }

    private class PointComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            double slopeTo1 = slopeTo(o1);
            double slopeTo2 = slopeTo(o2);
            return Double.compare(slopeTo1, slopeTo2);
        }
    }
}
