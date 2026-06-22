package com.luisbarrichello.api.ecommerce.service.auth;

import com.luisbarrichello.api.ecommerce.dto.auth.LoginUserDTO;
import com.luisbarrichello.api.ecommerce.dto.user.UserCreateDTO;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.user.UserRepository;
import com.luisbarrichello.api.ecommerce.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository, UserService userService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder, UserService userService1
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public User signup(UserCreateDTO input) throws IllegalAccessException {
        return userService.createUser(input);
    }

    public User authenticate(LoginUserDTO input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    input.login(),
                    input.password()
            )
        );
        return userRepository.findByEmail(input.login())
                .or(() -> userRepository.findByUsername(input.login()))
                .orElseThrow(() -> new RuntimeException("User not found after authentication!"));
    }
}
