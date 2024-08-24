package com.luisbarrichello.api.ecommerce.dto.user;

import com.luisbarrichello.api.ecommerce.model.user.User;

public record UserResponseDTO(
        Long id,
        String name,
        String phoneNumber,
        Boolean isActive
) {
    public UserResponseDTO(User user) {
        this(user.getId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getActive());
    }
}
