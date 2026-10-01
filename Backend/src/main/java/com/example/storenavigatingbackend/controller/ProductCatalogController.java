package com.example.storenavigatingbackend.controller;

import com.example.storenavigatingbackend.model.Product;
import com.example.storenavigatingbackend.service.ProductCatalogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // tighten this to your actual frontend origin before deployment
public class ProductCatalogController {

    private final ProductCatalogService productCatalogService;

    public ProductCatalogController(ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productCatalogService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productCatalogService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productCatalogService.getProductsByCategory(category));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(productCatalogService.getProductsByBrand(brand));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        return ResponseEntity.ok(productCatalogService.searchProducts(query));
    }

    @GetMapping("/deals")
    public ResponseEntity<List<Product>> getActiveDeals() {
        return ResponseEntity.ok(productCatalogService.getActiveDeals());
    }
}
