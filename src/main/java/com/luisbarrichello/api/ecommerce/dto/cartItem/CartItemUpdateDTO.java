package com.luisbarrichello.api.ecommerce.dto.cartItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CartItemUpdateDTO(
        @NotNull(message = "ID is mandatory")
        Long id,

        @NotNull
        @PositiveOrZero(message = "Quantity must be 0 or more")
        Integer quantity
) {
}
