package com.luisbarrichello.api.ecommerce.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginUserDTO(
        @NotBlank(message = "Login is mandatory")
        String login,

        @NotBlank(message = "Password is mandatory")
        String password
) {
}
