package com.luisbarrichello.api.ecommerce.dto.product;

import com.luisbarrichello.api.ecommerce.model.product.Product;

import java.math.BigDecimal;

public record ProductSummaryListDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imgUrl
) {
    public ProductSummaryListDTO(Product product) {
        this(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImgUrl());
    }
}
