package com.luisbarrichello.api.ecommerce.model.product;

import com.luisbarrichello.api.ecommerce.dto.product.ProductCreateDTO;
import com.luisbarrichello.api.ecommerce.model.category.Category;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "products")
@Entity(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private BigDecimal price;

    private int stock;

    @Enumerated(EnumType.STRING)
    private Category category; // Enum

    private String SKU;

    private String brand;

    private String imgUrl;

    private double weight;

    private double dimensions;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer salesCount;

    public Product(ProductCreateDTO productCreateDTO) {
        this.name = productCreateDTO.name();
        this.description = productCreateDTO.description();
        this.price = productCreateDTO.price();
        this.stock = productCreateDTO.stock();
        this.category = productCreateDTO.category();
        this.SKU = productCreateDTO.SKU();
        this.brand = productCreateDTO.brand();
        this.imgUrl = productCreateDTO.imgUrl();
        this.weight = productCreateDTO.weight();
        this.dimensions = productCreateDTO.dimensions();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public double getDimensions() {
        return dimensions;
    }

    public void setDimensions(double dimensions) {
        this.dimensions = dimensions;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalesCount() {
        return salesCount;
    }
}
