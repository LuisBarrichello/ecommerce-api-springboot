package com.luisbarrichello.api.ecommerce.dto.product;

import com.luisbarrichello.api.ecommerce.model.category.Category;

import java.math.BigDecimal;

public record ProductSearchDTO(
        String name,
        Category category,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String brand,
        String sortBy,
        Boolean ascending,
        Integer salesCount
) {
}
