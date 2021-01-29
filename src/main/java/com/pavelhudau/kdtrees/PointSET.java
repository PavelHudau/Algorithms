package com.pavelhudau.kdtrees;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> points = new SET<>();

    /**
     * Construct an empty set of points.
     */
    public PointSET() {
    }

    /**
     * Is the set empty?
     *
     * @return whether the set is empty.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Number of points in the set.
     *
     * @return number of points in the set.
     */
    public int size() {
        return this.points.size();
    }

    /**
     * Add the point to the set (if it is not already in the set)
     *
     * @param p a point to add.
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p can not be null");
        }
        this.points.add(p);
    }

    /**
     * does the set contain point p?
     *
     * @param p Point to check whether it's in the set.
     * @return whether the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p can not be null");
        }
        return this.points.contains(p);
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        for (Point2D p: this.points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    /**
     * All points that are inside the rectangle (or on the boundary).
     *
     * @param rect Rectangle inside which all points are.
     * @return Iterable of all points that are inside the rectangle (or on the boundary).
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect can not be null");
        }

        return () -> {
            ArrayList<Point2D> range = new ArrayList<>();
            for (Point2D p : points) {
                if(rect.contains(p)) {
                    range.add(p);
                }
            }
            return range.iterator();
        };
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p Point to find nearest neighbor to.
     * @return A nearest neighbor in the set to point p; null if the set is empty.
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p can not be null");
        }
        Point2D nearest = null;
        double smallestDistance = Double.POSITIVE_INFINITY;
        for (Point2D pp : points) {
            if(nearest != null) {
                double distanceToPP = p.distanceSquaredTo(pp);
                if(distanceToPP < smallestDistance) {
                    smallestDistance = distanceToPP;
                    nearest = pp;
                }
            }
            else{
                nearest = pp;
            }
        }
        return nearest;
    }
}
