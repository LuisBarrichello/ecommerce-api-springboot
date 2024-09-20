package com.luisbarrichello.api.ecommerce.dto.cartItem;

import com.luisbarrichello.api.ecommerce.model.category.Category;
import com.luisbarrichello.api.ecommerce.model.product.Product;

import java.math.BigDecimal;

public record CartItemCreateDTO(
        Long productId,
        Integer quantity
) {
}
