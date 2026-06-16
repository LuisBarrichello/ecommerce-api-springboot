package com.luisbarrichello.api.ecommerce.dto.cartItem;

import jakarta.validation.constraints.Min;

public record CartItemUpdateDTO(
        Long id,

        @Min(value = 0, message = "Quantity must be at least 0")
        Integer quantity
) {
}
