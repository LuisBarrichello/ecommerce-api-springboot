package com.luisbarrichello.api.ecommerce.dto.shippingService;

public record ShippingRequestDTO(
        String from,
        String to,
        DimensionsDTO dimensions
) {
}
