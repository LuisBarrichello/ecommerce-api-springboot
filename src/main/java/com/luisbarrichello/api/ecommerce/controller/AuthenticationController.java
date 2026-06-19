package com.luisbarrichello.api.ecommerce.controller;

import com.luisbarrichello.api.ecommerce.dto.auth.LoginResponseDTO;
import com.luisbarrichello.api.ecommerce.dto.auth.LoginUserDTO;
import com.luisbarrichello.api.ecommerce.dto.user.UserCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.user.UserResponseDTO;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.service.auth.AuthenticationService;
import com.luisbarrichello.api.ecommerce.service.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(
            JwtService jwtService,
            AuthenticationService authenticationService
    ) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserCreateDTO userCreateDTO) throws IllegalAccessException {
        User registeredUser = authenticationService.signup(userCreateDTO);
        UserResponseDTO userResponseDTO = new UserResponseDTO(registeredUser);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginUserDTO loginUserDTO) {
        User authenticatedUser = authenticationService.authenticate(loginUserDTO);

        String token = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO(
                token,
                jwtService.getExpirationTime()
        );

        return ResponseEntity.ok(loginResponse);
    }
}


