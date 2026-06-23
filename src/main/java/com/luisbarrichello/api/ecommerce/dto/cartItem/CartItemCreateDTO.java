package com.luisbarrichello.api.ecommerce.dto.cartItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemCreateDTO(
        @NotNull(message = "Product Id is mandatory")
        Long productId,

        @NotNull
        @Positive(message = "Quantity must be positive")
        Integer quantity
) {
}
