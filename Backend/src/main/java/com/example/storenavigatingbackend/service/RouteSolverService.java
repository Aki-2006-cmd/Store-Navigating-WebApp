package com.example.storenavigatingbackend.service;

import com.example.storenavigatingbackend.model.GridPoint;
import com.example.storenavigatingbackend.model.PathNode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteSolverService {

    private static final int MAX_X = 23;
    private static final int MAX_Y = 9;
    private final Set<GridPoint> obstacles = new HashSet<>();

    public RouteSolverService() {
        initializeObstacles();
    }

    private void initializeObstacles() {
        obstacles.clear();

        // Solid shelf blocks (Y = 2 to 7)
        int[] shelfXRanges = {4, 5, 7, 8, 10, 11, 13, 14, 16, 17, 19, 20};
        for (int x : shelfXRanges) {
            for (int y = 2; y <= 7; y++) {
                obstacles.add(new GridPoint(x, y));
            }
        }

        // Left & Right perimeter walls (keeping Y = 1 to 8 blocked on outer borders)
        for (int y = 1; y <= 8; y++) {
            obstacles.add(new GridPoint(0, y));
            obstacles.add(new GridPoint(1, y));
            obstacles.add(new GridPoint(22, y));
            obstacles.add(new GridPoint(23, y));
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
                double turnPenalty = 0.0;
                if (current.getParent() != null) {
                    int prevDx = current.getPoint().getX() - current.getParent().getPoint().getX();
                    int prevDy = current.getPoint().getY() - current.getParent().getPoint().getY();
                    int curDx = neighborPt.getX() - current.getPoint().getX();
                    int curDy = neighborPt.getY() - current.getPoint().getY();
                    if (prevDx != curDx || prevDy != curDy) {
                        turnPenalty = 0.2; // Favor straight corridor flow
                    }
                }

                double newGCost = current.getGCost() + 1.0 + turnPenalty;

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

        // Safe orthogonal fallback steps if needed
        List<GridPoint> fallback = new ArrayList<>();
        fallback.add(start);
        fallback.add(goal);
        return fallback;
    }

    public List<GridPoint> generateCompleteStoreRoute(GridPoint start, List<GridPoint> itemLocations, GridPoint checkout) {
        List<GridPoint> completeRoute = new ArrayList<>();
        
        if (itemLocations == null || itemLocations.isEmpty()) {
            return findPathBetweenPoints(start, checkout);
        }

        // Sort items left-to-right (by X coordinate) for logical store progression
        List<GridPoint> sortedItems = new ArrayList<>(itemLocations);
        sortedItems.sort(Comparator.comparingInt(GridPoint::getX));

        GridPoint currentPoint = start;

        for (GridPoint nextStop : sortedItems) {
            List<GridPoint> legPath = findPathBetweenPoints(currentPoint, nextStop);
            if (!completeRoute.isEmpty() && !legPath.isEmpty()) {
                legPath.remove(0); // Prevent duplicate overlapping points
            }
            completeRoute.addAll(legPath);
            currentPoint = nextStop;
        }

        // Final leg from last item waypoint through bottom corridor to checkout counter (18, 0)
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