package com.example.storenavigatingbackend.repository;

import com.example.storenavigatingbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByProductCategoryIgnoreCase(String category);

    List<Product> findByBrandIgnoreCase(String brand);

    List<Product> findByOfferFractionGreaterThan(Double threshold);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.productCategory) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchProducts(@Param("query") String query);
}