package com.luisbarrichello.api.ecommerce.repository.user;

import com.luisbarrichello.api.ecommerce.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
