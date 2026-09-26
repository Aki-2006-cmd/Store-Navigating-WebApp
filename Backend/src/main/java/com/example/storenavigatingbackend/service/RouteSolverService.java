package com.example.storenavigatingbackend.service;

import com.example.storenavigatingbackend.model.GridPoint;
import com.example.storenavigatingbackend.model.PathNode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteSolverService {

    private static final int MAX_X = 23;[cite: 1]
    private static final int MAX_Y = 10;[cite: 1]
    private final Set<GridPoint> obstacles = new HashSet<>();

    public RouteSolverService() {
        initializeObstacles();
    }

    private void initializeObstacles() {
        for (int y = 2; y <= 8; y++) {
            obstacles.add(new GridPoint(4, y));
            obstacles.add(new GridPoint(6, y));
            obstacles.add(new GridPoint(8, y));
            obstacles.add(new GridPoint(10, y));
            obstacles.add(new GridPoint(12, y));
            obstacles.add(new GridPoint(14, y));
        }
    }

    public List<GridPoint> findPathBetweenPoints(GridPoint start, GridPoint goal) {
        PriorityQueue<PathNode> openSet = new PriorityQueue<>();
        Map<GridPoint, PathNode> allNodes = new HashMap<>();

        PathNode startNode = new PathNode(start, 0, calculateHeuristic(start, goal), null);
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            PathNode current = openSet.poll();

            if (current.getPoint().equals(goal)) {
                return reconstructPath(current);
            }

            for (GridPoint neighborPt : getWalkableNeighbors(current.getPoint())) {
                double newGCost = current.getGCost() + 1.0;

                PathNode neighborNode = allNodes.get(neighborPt);
                if (neighborNode == null) {
                    neighborNode = new PathNode(neighborPt, newGCost, calculateHeuristic(neighborPt, goal), current);
                    allNodes.put(neighborPt, neighborNode);
                    openSet.add(neighborNode);
                } else if (newGCost < neighborNode.getGCost()) {
                    neighborNode.setGCost(newGCost);
                    neighborNode.setParent(current);
                    openSet.remove(neighborNode);
                    openSet.add(neighborNode);
                }
            }
        }

        return Collections.emptyList();
    }

    public List<GridPoint> generateCompleteStoreRoute(GridPoint start, List<GridPoint> itemLocations, GridPoint checkout) {
        List<GridPoint> unvisited = new ArrayList<>(itemLocations);
        List<GridPoint> completeRoute = new ArrayList<>();
        GridPoint currentPoint = start;

        while (!unvisited.isEmpty()) {
            GridPoint nearest = null;
            double minDistance = Double.MAX_VALUE;

            for (GridPoint item : unvisited) {
                double dist = calculateHeuristic(currentPoint, item);
                if (dist < minDistance) {
                    minDistance = dist;
                    nearest = item;
                }
            }

            List<GridPoint> legPath = findPathBetweenPoints(currentPoint, nearest);
            if (!completeRoute.isEmpty() && !legPath.isEmpty()) {
                legPath.remove(0);
            }
            completeRoute.addAll(legPath);

            currentPoint = nearest;
            unvisited.remove(nearest);
        }

        List<GridPoint> finalLeg = findPathBetweenPoints(currentPoint, checkout);
        if (!completeRoute.isEmpty() && !finalLeg.isEmpty()) {
            finalLeg.remove(0);
        }
        completeRoute.addAll(finalLeg);

        return completeRoute;
    }

    private double calculateHeuristic(GridPoint p1, GridPoint p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    private List<GridPoint> getWalkableNeighbors(GridPoint point) {
        List<GridPoint> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] dir : directions) {
            int newX = point.getX() + dir[0];
            int newY = point.getY() + dir[1];
            GridPoint neighbor = new GridPoint(newX, newY);

            if (isValidCoordinate(newX, newY) && !obstacles.contains(neighbor)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x <= MAX_X && y >= 0 && y <= MAX_Y;
    }

    private List<GridPoint> reconstructPath(PathNode node) {
        List<GridPoint> path = new LinkedList<>();
        PathNode curr = node;
        while (curr != null) {
            path.add(0, curr.getPoint());
            curr = curr.getParent();
        }
        return path;
    }
}