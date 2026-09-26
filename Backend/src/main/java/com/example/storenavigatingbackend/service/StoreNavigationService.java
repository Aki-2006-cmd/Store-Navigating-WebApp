package com.example.storenavigatingbackend.service;

import com.example.storenavigatingbackend.model.GridPoint;
import com.example.storenavigatingbackend.model.Product;
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
        if (product.getProductIsle() != null) {[cite: 5]
            int aisle = product.getProductIsle();[cite: 5]
            int xCoord = Math.min(Math.max(aisle * 2, 1), 22);
            return new GridPoint(xCoord, 5);
        }

        String category = product.getProductCategory() != null ? product.getProductCategory().toLowerCase() : "";[cite: 5]
        return switch (category) {
            case "dairy" -> new GridPoint(2, 9);
            case "cooking ingredients" -> new GridPoint(6, 5);
            case "baking", "flour & baking" -> new GridPoint(10, 5);
            case "meat" -> new GridPoint(16, 9);
            case "seafood" -> new GridPoint(20, 9);
            case "snacks" -> new GridPoint(22, 3);
            default -> new GridPoint(12, 5);
        };
    }

    public List<GridPoint> calculateCartNavigationRoute(List<String> productIds) {
        List<Product> products = productRepository.findAllById(productIds);

        List<GridPoint> itemWaypoints = new ArrayList<>();
        for (Product product : products) {
            itemWaypoints.add(mapProductToCoordinate(product));
        }

        GridPoint startPoint = new GridPoint(2, 0);   // Entrance/Carts (y=0)[cite: 1, 3]
        GridPoint checkoutPoint = new GridPoint(18, 0); // Checkout (y=0)[cite: 1, 3]

        return routeSolverService.generateCompleteStoreRoute(startPoint, itemWaypoints, checkoutPoint);
    }
}