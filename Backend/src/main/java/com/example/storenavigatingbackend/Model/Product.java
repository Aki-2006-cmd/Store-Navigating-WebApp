package com.example.storenavigatingbackend.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import jakarta.persistence.*;

@Entity
@Table(name = "main_table")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Product_name", nullable = false)
    private String productName;

    @Column(name = "Product_image")
    private String productImage;

    @Column(name = "X_cordinate", nullable = false)
    private Integer xCoordinate;

    @Column(name = "Y_cordinate", nullable = false)
    private Integer yCoordinate;

    @Column(name = "Z_cordinate", nullable = false)
    private Integer zCoordinate;

    @Column(name = "Shelf_number", nullable = false)
    private Integer productIsle;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Remaining_quantity")
    private Integer remainingQuantity;

    @Column(name = "Catagory")
    private String productCategory;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Offer")
    private Double offerFraction;

    public Product() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public Integer getXCoordinate() { return xCoordinate; }
    public void setXCoordinate(Integer xCoordinate) { this.xCoordinate = xCoordinate; }

    public Integer getYCoordinate() { return yCoordinate; }
    public void setYCoordinate(Integer yCoordinate) { this.yCoordinate = yCoordinate; }

    public Integer getZCoordinate() { return zCoordinate; }
    public void setZCoordinate(Integer zCoordinate) { this.zCoordinate = zCoordinate; }

    public Integer getProductIsle() { return productIsle; }
    public void setProductIsle(Integer productIsle) { this.productIsle = productIsle; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getRemainingQuantity() { return remainingQuantity; }
    public void setRemainingQuantity(Integer remainingQuantity) { this.remainingQuantity = remainingQuantity; }

    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Double getOfferFraction() { return offerFraction; }
    public void setOfferFraction(Double offerFraction) { this.offerFraction = offerFraction; }

    public BigDecimal getEffectivePrice() {
        if (price == null) return BigDecimal.ZERO;
        if (offerFraction == null || offerFraction <= 0.0) return price;

        BigDecimal discount = price.multiply(BigDecimal.valueOf(offerFraction));
        return price.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }
}