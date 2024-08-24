package com.luisbarrichello.api.ecommerce.dto.product;

import com.luisbarrichello.api.ecommerce.model.category.Category;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        String name,
        String description,
        BigDecimal price,
        int stock,
        Category category,
        String SKU,
        String brand,
        String imgUrl,
        double weight,
        double dimensions
) {
}
