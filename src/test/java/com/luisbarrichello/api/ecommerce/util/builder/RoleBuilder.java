package com.luisbarrichello.api.ecommerce.util.builder;

import com.luisbarrichello.api.ecommerce.model.role.Role;

public class RoleBuilder {
    private Long id = 1L;
    private String name = "ROLE_CUSTOMER";

    public RoleBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Role build() {
        Role role = new Role();
        role.setId(this.id);
        role.setName(this.name);
        return role;
    }
}