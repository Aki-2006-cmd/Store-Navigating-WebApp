package com.example.storenavigatingbackend.controller;

import com.example.storenavigatingbackend.model.GridPoint;
import com.example.storenavigatingbackend.model.Product;
import com.example.storenavigatingbackend.model.RouteResponsedto;
import com.example.storenavigatingbackend.service.ProductCatalogService;
import com.example.storenavigatingbackend.service.StoreNavigationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/navigation")
@CrossOrigin(origins = "*")
public class NavigationRouteController {

    private final StoreNavigationService storeNavigationService;
    private final ProductCatalogService productCatalogService;

    public NavigationRouteController(StoreNavigationService storeNavigationService,
                                      ProductCatalogService productCatalogService) {
        this.storeNavigationService = storeNavigationService;
        this.productCatalogService = productCatalogService;
    }

    // "Find the location of a product" — single-item lookup
    @GetMapping("/locate/{productId}")
    public ResponseEntity<GridPoint> locateProduct(@PathVariable Long productId) {
        Optional<Product> product = productCatalogService.getProductById(productId);
        return product
                .map(storeNavigationService::mapProductToCoordinate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // "Given a grocery list, generate the shortest-time route" — full cart route
    @PostMapping("/route")
    public ResponseEntity<RouteResponsedto> generateShoppingRoute(@RequestBody List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        RouteResponsedto routeResponse = storeNavigationService.calculateCartNavigationRoute(productIds);
        return ResponseEntity.ok(routeResponse);
    }
}   