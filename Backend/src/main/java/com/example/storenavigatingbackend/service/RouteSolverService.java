package com.example.storenavigatingbackend.service;

import com.example.storenavigatingbackend.model.GridPoint;
import com.example.storenavigatingbackend.model.PathNode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteSolverService {

    private static final int MAX_X = 23;
    private static final int MAX_Y = 10;
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
        if (itemLocations == null || itemLocations.isEmpty()) {
            return findPathBetweenPoints(start, checkout);
        }

        // Optimize stop sequence using TSP (Nearest Neighbor + 2-Opt local search refinement)
        List<GridPoint> orderedStops = optimizeTspTour(start, itemLocations);
        List<GridPoint> completeRoute = new ArrayList<>();
        GridPoint currentPoint = start;

        for (GridPoint nextStop : orderedStops) {
            List<GridPoint> legPath = findPathBetweenPoints(currentPoint, nextStop);
            if (!completeRoute.isEmpty() && !legPath.isEmpty()) {
                legPath.remove(0); // Avoid duplicating the connection node
            }
            completeRoute.addAll(legPath);
            currentPoint = nextStop;
        }

        List<GridPoint> finalLeg = findPathBetweenPoints(currentPoint, checkout);
        if (!completeRoute.isEmpty() && !finalLeg.isEmpty()) {
            finalLeg.remove(0);
        }
        completeRoute.addAll(finalLeg);

        return completeRoute;
    }

    private List<GridPoint> optimizeTspTour(GridPoint start, List<GridPoint> locations) {
        // Step 1: Initial Nearest Neighbor tour generation
        List<GridPoint> tour = new ArrayList<>();
        List<GridPoint> unvisited = new ArrayList<>(locations);
        GridPoint current = start;

        while (!unvisited.isEmpty()) {
            GridPoint nearest = null;
            double minDist = Double.MAX_VALUE;

            for (GridPoint pt : unvisited) {
                double dist = calculateHeuristic(current, pt);
                if (dist < minDist) {
                    minDist = dist;
                    nearest = pt;
                }
            }
            tour.add(nearest);
            current = nearest;
            unvisited.remove(nearest);
        }

        // Step 2: 2-opt optimization pass to remove unnecessary crisscrossing
        boolean improvement = true;
        int maxIterations = 50;
        int iteration = 0;

        while (improvement && iteration < maxIterations) {
            improvement = false;
            iteration++;

            for (int i = 0; i < tour.size() - 1; i++) {
                for (int j = i + 1; j < tour.size(); j++) {
                    List<GridPoint> newTour = reverseTourSegment(tour, i, j);
                    if (calculateTourCost(start, newTour) < calculateTourCost(start, tour)) {
                        tour = newTour;
                        improvement = true;
                    }
                }
            }
        }

        return tour;
    }

    private List<GridPoint> reverseTourSegment(List<GridPoint> tour, int i, int j) {
        List<GridPoint> modified = new ArrayList<>(tour);
        while (i < j) {
            GridPoint temp = modified.get(i);
            modified.set(i, modified.get(j));
            modified.set(j, temp);
            i++;
            j--;
        }
        return modified;
    }

    private double calculateTourCost(GridPoint start, List<GridPoint> tour) {
        if (tour.isEmpty()) return 0;
        double cost = calculateHeuristic(start, tour.get(0));
        for (int i = 0; i < tour.size() - 1; i++) {
            cost += calculateHeuristic(tour.get(i), tour.get(i + 1));
        }
        return cost;
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