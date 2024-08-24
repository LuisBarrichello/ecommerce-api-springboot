package com.luisbarrichello.api.ecommerce.dto.product;

import com.luisbarrichello.api.ecommerce.model.category.Category;
import com.luisbarrichello.api.ecommerce.model.product.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
         String name,
         String description,
         BigDecimal price,
         int stock,
         Category category,
         String SKU,
         String brand,
         String imgUrl,
        double weigth,
        double dimensions
) {
    public ProductResponseDTO(Product product) {
        this(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getSKU(),
                product.getBrand(),
                product.getImgUrl(),
                product.getWeight(),
                product.getDimensions()
        );
    }
}
