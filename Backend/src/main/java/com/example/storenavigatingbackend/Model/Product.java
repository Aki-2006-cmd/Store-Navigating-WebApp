package com.example.storenavigatingbackend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private String id;

    @Column(name = "product_name")
    private String productName;

    private String brand;
    private BigDecimal price;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_isle")
    private Integer productIsle;

    @Column(name = "offer_fraction")
    private Double offerFraction;

    public Product() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }

    public Integer getProductIsle() { return productIsle; }
    public void setProductIsle(Integer productIsle) { this.productIsle = productIsle; }

    public Double getOfferFraction() { return offerFraction; }
    public void setOfferFraction(Double offerFraction) { this.offerFraction = offerFraction; }

    public BigDecimal getEffectivePrice() {
        if (price == null) return BigDecimal.ZERO;
        if (offerFraction == null || offerFraction <= 0.0) return price;

        BigDecimal discount = price.multiply(BigDecimal.valueOf(offerFraction));
        return price.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }
}