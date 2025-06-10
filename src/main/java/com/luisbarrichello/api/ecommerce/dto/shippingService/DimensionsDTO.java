package com.luisbarrichello.api.ecommerce.dto.shippingService;

public record DimensionsDTO(
        Double height,
        Double width,
        Double length,
        Double weight
) {
}