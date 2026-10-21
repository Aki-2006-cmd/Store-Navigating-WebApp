package com.example.storenavigatingbackend.service;

import com.example.storenavigatingbackend.model.GridPoint;
import com.example.storenavigatingbackend.model.Product;
import com.example.storenavigatingbackend.model.RouteResponsedto;
import com.example.storenavigatingbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StoreNavigationService {

    private final ProductRepository productRepository;
    private final RouteSolverService routeSolverService;

    public StoreNavigationService(ProductRepository productRepository, RouteSolverService routeSolverService) {
        this.productRepository = productRepository;
        this.routeSolverService = routeSolverService;
    }

    public GridPoint mapProductToCoordinate(Product product) {
        int dbX = product.getXCoordinate();
        int dbY = product.getYCoordinate();

        String productName = product.getProductName() != null ? product.getProductName().toLowerCase() : "";

        // Specific override for products located at the very top/end of Aisle 1 (like Soy Milk)
        if (productName.contains("soy milk")) {
            return new GridPoint(3, 7); // Top end of Aisle 1 walkway
        }

        int gridY = Math.max(1, Math.min(8, dbY));
        int gridX = Math.max(2, Math.min(21, dbX));

        if (isShelfObstacle(gridX, gridY)) {
            gridX = getAdjacentWalkwayColumn(gridX);
        }

        return new GridPoint(gridX, gridY);
    }

    private boolean isShelfObstacle(int x, int y) {
        if (y < 2 || y > 7) return false;
        int[] shelfXColumns = {4, 5, 7, 8, 10, 11, 13, 14, 16, 17, 19, 20};
        for (int sx : shelfXColumns) {
            if (sx == x) return true;
        }
        return false;
    }

    private int getAdjacentWalkwayColumn(int shelfX) {
        if (shelfX <= 5) return 3;   // Aisle 1
        if (shelfX <= 8) return 6;   // Aisle 2
        if (shelfX <= 11) return 9;  // Aisle 3
        if (shelfX <= 14) return 12; // Aisle 4
        if (shelfX <= 17) return 15; // Aisle 5
        return 18;                   // Aisle 6
    }

    public RouteResponsedto calculateCartNavigationRoute(List<Long> productIds) {
        List<Product> products = productRepository.findAllById(productIds);

        List<GridPoint> itemWaypoints = new ArrayList<>();
        List<RouteResponsedto.ProductPin> productPins = new ArrayList<>();

        for (Product product : products) {
            // 1. Snapped walkway coordinate used strictly for Dijkstra pathfinding
            itemWaypoints.add(mapProductToCoordinate(product));

            // 2. Raw database shelf coordinate used strictly for UI rendering/pins
            int rawX = product.getXCoordinate();
            int rawY = product.getYCoordinate();
            String name = product.getProductName();

            productPins.add(new RouteResponsedto.ProductPin(product.getId(), name, rawX, rawY));
        }

        // Carts Starting Point at (3, 0)
        GridPoint startPoint = new GridPoint(3, 0);
        // Checkout Counters Ending Point at (18, 0)
        GridPoint checkoutPoint = new GridPoint(18, 0);

        List<GridPoint> fullRoute = routeSolverService.generateCompleteStoreRoute(startPoint, itemWaypoints, checkoutPoint);

        return new RouteResponsedto(fullRoute, productPins);
    }
}