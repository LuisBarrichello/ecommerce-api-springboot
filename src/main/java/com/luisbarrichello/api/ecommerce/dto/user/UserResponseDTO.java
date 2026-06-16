package com.luisbarrichello.api.ecommerce.dto.user;

import com.luisbarrichello.api.ecommerce.model.user.User;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String username,
        Boolean isActive
) {
    public UserResponseDTO(User user) {
        this(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getActive());
    }
}
