package com.example.storenavigatingbackend.controller;

import com.example.storenavigatingbackend.service.ProductCatalogService;
import com.example.storenavigatingbackend.service.ProductCatalogService.CartItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartValidationController {

    private final ProductCatalogService productCatalogService;

    public CartValidationController(ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }

    // Real-time price, stock limits & subtotal for the current cart contents
    @PostMapping("/summary")
    public ResponseEntity<?> calculateCartSummary(@RequestBody List<CartItemRequest> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cart is empty"));
        }
        try {
            Map<String, Object> summary = productCatalogService.calculateCartSummary(cartItems);
            return ResponseEntity.ok(summary);
        } catch (IllegalArgumentException ex) {
            // thrown by the service when a productId in the cart doesn't exist
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }
}
