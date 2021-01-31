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
        if (this.root == null) {
            return matchingPoints;
        }

        RectHV nodeRect = new RectHV(0, 0, 1, 1);
        range(this.root, nodeRect, rect, matchingPoints);
        return matchingPoints;
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
        if (this.root == null) {
            return null;
        }
        return nearest(p, this.root, new RectHV(0, 0, 1, 1));
    }

    private static boolean goesLeft(Point2D p, Node current) {
        return current.isBlue
                ? p.x() < current.point.x()
                : p.y() < current.point.y();
    }

    private static RectHV rectLeft(RectHV rectToSlice, Point2D slicingPoint) {
        return new RectHV(
                rectToSlice.xmin(),
                rectToSlice.ymin(),
                slicingPoint.x(),
                rectToSlice.ymax());
    }

    private static RectHV rectRight(RectHV rectToSlice, Point2D slicingPoint) {
        return new RectHV(
                slicingPoint.x(),
                rectToSlice.ymin(),
                rectToSlice.xmax(),
                rectToSlice.ymax());
    }

    private static RectHV rectTop(RectHV rectToSlice, Point2D slicingPoint) {
        return new RectHV(
                rectToSlice.xmin(),
                slicingPoint.y(),
                rectToSlice.xmax(),
                rectToSlice.ymax());
    }

    private static RectHV rectBottom(RectHV rectToSlice, Point2D slicingPoint) {
        return new RectHV(
                rectToSlice.xmin(),
                rectToSlice.ymin(),
                rectToSlice.xmax(),
                slicingPoint.y());
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

    private void range(Node currentNode, RectHV nodeRect, RectHV searchRect, ArrayList<Point2D> matchingPoints) {
        // Algorithm:
        // - split nodeRect
        // - recursively go left, right, top and bottom splitting nodeRect either vertically or horizontally
        if (searchRect.contains(currentNode.point)) {
            matchingPoints.add(currentNode.point);
        }
        if (currentNode.isBlue) {
            if (currentNode.left != null) {
                RectHV leftRect = rectLeft(nodeRect, currentNode.point);
                if (leftRect.intersects(searchRect)) {
                    range(currentNode.left, leftRect, searchRect, matchingPoints);
                }
            }
            if (currentNode.right != null) {
                RectHV rightRect = rectRight(nodeRect, currentNode.point);
                if (rightRect.intersects(searchRect)) {
                    range(currentNode.right, rightRect, searchRect, matchingPoints);
                }
            }
        } else {
            if (currentNode.left != null) {
                RectHV bottomRect = rectBottom(nodeRect, currentNode.point);
                if (bottomRect.intersects(searchRect)) {
                    range(currentNode.left, bottomRect, searchRect, matchingPoints);
                }
            }
            if (currentNode.right != null) {
                RectHV topRect = rectTop(nodeRect, currentNode.point);
                if (topRect.intersects(searchRect)) {
                    range(currentNode.right, topRect, searchRect, matchingPoints);
                }
            }
        }
    }

    private Point2D nearest(Point2D searchPoint, Node currentNode, RectHV nodeRect) {
        // Algorithm:
        // To find a closest point to a given query point, start at the root and recursively search in both subtrees
        // using the following pruning rule: if the closest point discovered so far is closer than the distance between
        // the query point and the rectangle corresponding to a node, there is no need to explore that node
        // (or its subtrees). That is, search a node only only if it might contain a point that is closer than the best
        // one found so far. The effectiveness of the pruning rule depends on quickly finding a nearby point. To do
        // this, organize the recursive method so that when there are two possible subtrees to go down, you always
        // choose the subtree that is on the same side of the splitting line as the query point as the first subtree to
        // explore—the closest point found while exploring the first subtree may enable pruning of the second subtree.
        Point2D nearestSoFar = currentNode.point;
        double nearestSoFarSqDistance = searchPoint.distanceSquaredTo(currentNode.point);
        if (currentNode.isBlue) {
            if (currentNode.left != null) {
                RectHV leftRect = rectLeft(nodeRect, currentNode.point);
                if (leftRect.distanceSquaredTo(searchPoint) < nearestSoFarSqDistance) {
                    Point2D mayBeNearest = nearest(searchPoint, currentNode.left, leftRect);
                    double mayBeNearestSqDistance = searchPoint.distanceSquaredTo(mayBeNearest);
                    if (mayBeNearestSqDistance < nearestSoFarSqDistance) {
                        nearestSoFar = mayBeNearest;
                        nearestSoFarSqDistance = mayBeNearestSqDistance;
                    }
                }
            }
            if (currentNode.right != null) {
                RectHV rightRect = rectRight(nodeRect, currentNode.point);
                if (rightRect.distanceSquaredTo(searchPoint) < nearestSoFarSqDistance) {
                    Point2D mayBeNearest = nearest(searchPoint, currentNode.right, rightRect);
                    double mayBeNearestSqDistance = searchPoint.distanceSquaredTo(mayBeNearest);
                    if (mayBeNearestSqDistance < nearestSoFarSqDistance) {
                        nearestSoFar = mayBeNearest;
                    }
                }
            }
        } else {
            if (currentNode.left != null) {
                RectHV bottomRect = rectBottom(nodeRect, currentNode.point);
                if (bottomRect.distanceSquaredTo(searchPoint) < nearestSoFarSqDistance) {
                    Point2D mayBeNearest = nearest(searchPoint, currentNode.left, bottomRect);
                    double mayBeNearestSqDistance = searchPoint.distanceSquaredTo(mayBeNearest);
                    if (mayBeNearestSqDistance < nearestSoFarSqDistance) {
                        nearestSoFar = mayBeNearest;
                        nearestSoFarSqDistance = mayBeNearestSqDistance;
                    }
                }
            }
            if (currentNode.right != null) {
                RectHV topRect = rectTop(nodeRect, currentNode.point);
                if (topRect.distanceSquaredTo(searchPoint) < nearestSoFarSqDistance) {
                    Point2D mayBeNearest = nearest(searchPoint, currentNode.right, topRect);
                    double mayBeNearestSqDistance = searchPoint.distanceSquaredTo(mayBeNearest);
                    if (mayBeNearestSqDistance < nearestSoFarSqDistance) {
                        nearestSoFar = mayBeNearest;
                    }
                }
            }
        }

        return nearestSoFar;
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
