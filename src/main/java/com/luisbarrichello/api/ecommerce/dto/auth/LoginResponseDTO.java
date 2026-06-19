package com.luisbarrichello.api.ecommerce.dto.auth;

public record LoginResponseDTO(
    String token,
    long expiresIn
) {
}
