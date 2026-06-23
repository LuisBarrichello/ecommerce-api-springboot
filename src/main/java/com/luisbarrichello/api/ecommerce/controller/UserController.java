package com.luisbarrichello.api.ecommerce.controller;

import com.luisbarrichello.api.ecommerce.dto.user.UserResponseDTO;
import com.luisbarrichello.api.ecommerce.dto.user.UserUpdateDTO;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable @Valid Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        User user = userService.updateUser(userUpdateDTO, id);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable @Valid Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        UserResponseDTO userResponseDTO = new UserResponseDTO(currentUser);
        return ResponseEntity.ok(userResponseDTO);
    }
}

