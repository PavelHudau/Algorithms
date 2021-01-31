package com.pavelhudau.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int treeSize = 0;

    public KdTree() {
    }

    /**
     * Is the set empty?
     *
     * @return whether the set is empty.
     */
    public boolean isEmpty() {
        return this.treeSize < 1;
    }

    /**
     * Number of points in the set.
     *
     * @return number of points in the set.
     */
    public int size() {
        return this.treeSize;
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
        Node insertedNode;
        if (this.root != null) {
            insertedNode = this.insertPoint(p, this.root);
        } else {
            insertedNode = new Node(p, false);
            this.root = insertedNode;
        }
        if (insertedNode != null) {
            this.treeSize++;
        }
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
        return this.containsPoint(p, this.root);
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        drawPoint(this.root);
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

        ArrayList<Point2D> matchingPoints = new ArrayList<>();
        if(this.root == null) {
            return matchingPoints;
        }

        RectHV nodeRect = new RectHV(0, 0, 1, 1);
        range(this.root, nodeRect, rect, matchingPoints);
        return matchingPoints;
    }

    private void range(Node currentNode, RectHV nodeRect, RectHV searchRect, ArrayList<Point2D> matchingPoints) {
        //TODO: 1. split nodeRect, 2. go left or right or top, or bottom splitting nodeRect
        if(searchRect.contains(currentNode.point)) {
            matchingPoints.add(currentNode.point);
        }
        if(currentNode.isBlue) {
            if(currentNode.left != null) {
                RectHV leftRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), currentNode.point.x(), nodeRect.ymax());
                if (leftRect.intersects(searchRect)) {
                    range(currentNode.left, leftRect, searchRect, matchingPoints);
                }
            }
            if(currentNode.right != null) {
                RectHV rightRect = new RectHV(currentNode.point.x(), nodeRect.ymin(), nodeRect.xmax(), nodeRect.ymax());
                if (rightRect.intersects(searchRect)) {
                    range(currentNode.right, rightRect, searchRect, matchingPoints);
                }
            }
        }
        else {
            if(currentNode.left != null) {
                RectHV bottomRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), nodeRect.xmax(), currentNode.point.y());
                if (bottomRect.intersects(searchRect)) {
                    range(currentNode.left, bottomRect, searchRect, matchingPoints);
                }
            }
            if(currentNode.right != null) {
                RectHV topRect = new RectHV(nodeRect.xmin(), currentNode.point.y(), nodeRect.xmax(), nodeRect.ymax());
                if (topRect.intersects(searchRect)) {
                    range(currentNode.right, topRect, searchRect, matchingPoints);
                }
            }
        }
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
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    private static boolean goesLeft(Point2D p, Node current) {
        return current.isBlue
                ? p.x() < current.point.x()
                : p.y() < current.point.y();
    }

    private Node insertPoint(Point2D p, Node current) {
        if (p.equals(current.point)) {
            return null;
        }
        if (goesLeft(p, current)) {
            if (current.left != null) {
                return insertPoint(p, current.left);
            } else {
                current.left = new Node(p, !current.isBlue);
                return current.left;
            }
        } else {
            if (current.right != null) {
                return insertPoint(p, current.right);
            } else {
                current.right = new Node(p, !current.isBlue);
                return current.right;
            }
        }
    }

    private boolean containsPoint(Point2D p, Node current) {
        if (current == null) {
            return false;
        }
        if (p.equals(current.point)) {
            return true;
        }
        if (goesLeft(p, current)) {
            return containsPoint(p, current.left);
        } else {
            return containsPoint(p, current.right);
        }
    }

    private void drawPoint(Node current) {
        if (current == null) {
            return;
        }
        StdDraw.point(current.point.x(), current.point.y());
        drawPoint(current.left);
        drawPoint(current.right);
    }

    private static class Node {
        private final Point2D point;
        private final boolean isBlue;
        private Node left = null;
        private Node right = null;

        public Node(Point2D point, boolean isBlue) {
            this.point = point;
            this.isBlue = isBlue;
        }
    }
}
