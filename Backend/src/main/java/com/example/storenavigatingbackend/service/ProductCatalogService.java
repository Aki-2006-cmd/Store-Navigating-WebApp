package com.example.storenavigatingbackend.service;

import com.example.storenavigatingbackend.model.Product;
import com.example.storenavigatingbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductCatalogService {

    private final ProductRepository productRepository;

    public ProductCatalogService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByProductCategoryIgnoreCase(category);
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrandIgnoreCase(brand);
    }

    public List<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return productRepository.searchProducts(query.trim());
    }

    public List<Product> getActiveDeals() {
        return productRepository.findByOfferFractionGreaterThan(0.000);
    }

    public Map<String, Object> calculateCartSummary(List<CartItemRequest> cartItems) {
        BigDecimal grossTotal = BigDecimal.ZERO;
        BigDecimal totalSavings = BigDecimal.ZERO;
        List<Map<String, Object>> processedItems = new ArrayList<>();

        for (CartItemRequest item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProductId()));

            BigDecimal originalPrice = product.getPrice();
            BigDecimal effectivePrice = product.getEffectivePrice();
            int quantity = item.getQuantity();

            BigDecimal lineGross = originalPrice.multiply(BigDecimal.valueOf(quantity));
            BigDecimal lineNet = effectivePrice.multiply(BigDecimal.valueOf(quantity));
            BigDecimal lineDiscount = lineGross.subtract(lineNet);

            grossTotal = grossTotal.add(lineGross);
            totalSavings = totalSavings.add(lineDiscount);

            Map<String, Object> itemDetail = new HashMap<>();
            itemDetail.put("id", product.getId());
            itemDetail.put("productName", product.getProductName());
            itemDetail.put("brand", product.getBrand());
            itemDetail.put("category", product.getProductCategory());
            itemDetail.put("aisle", product.getProductIsle());
            itemDetail.put("originalPrice", originalPrice);
            itemDetail.put("effectivePrice", effectivePrice);
            itemDetail.put("quantity", quantity);
            itemDetail.put("lineTotal", lineNet);

            processedItems.add(itemDetail);
        }

        BigDecimal finalSubtotal = grossTotal.subtract(totalSavings);

        Map<String, Object> response = new HashMap<>();
        response.put("items", processedItems);
        response.put("grossTotal", grossTotal);
        response.put("totalSavings", totalSavings);
        response.put("subtotal", finalSubtotal);

        return response;
    }

    public static class CartItemRequest {
        private String productId;
        private int quantity;

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}