package com.luisbarrichello.api.ecommerce.dto.user;

import com.luisbarrichello.api.ecommerce.model.address.Address;
import com.luisbarrichello.api.ecommerce.model.user.RoleUser;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserUpdateDTO(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Username is mandatory")
        String username,

        @NotBlank(message = "Phone is mandatory")
        String phoneNumber
) {
}
