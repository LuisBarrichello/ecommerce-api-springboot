package com.luisbarrichello.api.ecommerce.util.builder;

import com.luisbarrichello.api.ecommerce.model.role.Role;
import com.luisbarrichello.api.ecommerce.model.user.User;

public class UserBuilder {
    private Long id = 1L;
    private String name = "Luis Barrichello";
    private String email = "admin@email.com";
    private String username = "BARRICHELLO";
    private String password = "password123";
    private String phoneNumber = "11999999999";
    private Role role = new RoleBuilder().build();

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setPhoneNumber(this.phoneNumber);
        user.setRole(this.role);
        user.setActive(true);
        return user;
    }
}