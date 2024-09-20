package com.luisbarrichello.api.ecommerce.dto.user;

import com.luisbarrichello.api.ecommerce.model.address.Address;
import com.luisbarrichello.api.ecommerce.model.user.RoleUser;

import java.util.List;

public record UserUpdateDTO(
        String name,
        String username,
        String phoneNumber
) {
}
