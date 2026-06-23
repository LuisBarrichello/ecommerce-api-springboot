package com.luisbarrichello.api.ecommerce.dto.user;

import com.luisbarrichello.api.ecommerce.model.address.Address;
import com.luisbarrichello.api.ecommerce.model.role.Role;
import com.luisbarrichello.api.ecommerce.model.user.RoleUser;
import jakarta.validation.constraints.*;

import java.util.List;

public record UserCreateDTO(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password is mandatory")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Username is mandatory")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @NotBlank
        String phoneNumber,

        @NotNull
        Long roleId,
        List<Address> address
) {
}