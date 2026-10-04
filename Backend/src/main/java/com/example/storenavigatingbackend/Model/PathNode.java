package com.example.storenavigatingbackend.model;

public class PathNode implements Comparable<PathNode> {
    private GridPoint point;
    private double gCost;
    private double hCost;
    private PathNode parent;

    public PathNode(GridPoint point, double gCost, double hCost, PathNode parent) {
        this.point = point;
        this.gCost = gCost;
        this.hCost = hCost;
        this.parent = parent;
    }

    public double getFCost() {
        return gCost + hCost;
    }

    public GridPoint getPoint() { return point; }

    public double getGCost() { return gCost; }
    public void setGCost(double gCost) { this.gCost = gCost; }

    public PathNode getParent() { return parent; }
    public void setParent(PathNode parent) { this.parent = parent; }

    @Override
    public int compareTo(PathNode other) {
        return Double.compare(this.getFCost(), other.getFCost());
    }
}