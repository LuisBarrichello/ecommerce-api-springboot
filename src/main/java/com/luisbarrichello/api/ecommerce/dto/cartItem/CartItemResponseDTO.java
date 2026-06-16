package com.luisbarrichello.api.ecommerce.dto.cartItem;

import com.luisbarrichello.api.ecommerce.model.cartItem.CartItem;
import com.luisbarrichello.api.ecommerce.model.product.Product;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        Long id,
        Product product,
        Integer quantity,
        BigDecimal price
) {
    public CartItemResponseDTO(CartItem cartItem) {
        this(
                cartItem.getId(),
                cartItem.getProduct(),
                cartItem.getQuantity(),
                cartItem.getPrice()
        );
    }
}
