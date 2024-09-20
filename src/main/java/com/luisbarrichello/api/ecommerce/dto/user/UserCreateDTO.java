package com.luisbarrichello.api.ecommerce.dto.user;

import com.luisbarrichello.api.ecommerce.model.address.Address;
import com.luisbarrichello.api.ecommerce.model.user.RoleUser;

import java.util.List;

public record UserCreateDTO(
        String name,
        String email,
        String password,
        String username,
        String phoneNumber,
        RoleUser role,
        List<Address> address
) {
}
