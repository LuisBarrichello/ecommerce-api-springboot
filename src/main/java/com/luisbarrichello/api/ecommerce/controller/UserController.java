package com.luisbarrichello.api.ecommerce.controller;

import com.luisbarrichello.api.ecommerce.dto.user.UserCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.user.UserResponseDTO;
import com.luisbarrichello.api.ecommerce.dto.user.UserUpdateDTO;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.user.UserRepository;
import com.luisbarrichello.api.ecommerce.service.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity registerUser(@RequestBody @Valid UserCreateDTO userCreateDTO, UriBuilder uriBuilder) {
        return null;
//        User user = userService.createUser(userCreateDTO);
//        userRepository.save(user);
//
//        var uri = uriBuilder.path("users/{id}").buildAndExpand(user.getId()).toUri();
//        return ResponseEntity.created(uri).body(new UserResponseDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable @Valid Long id) {
        UserResponseDTO user = userRepository.findById(id)
                .map(UserResponseDTO::new)
                .orElseThrow(() -> new ValidationException("Usuário não encontrado"));
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> listUsers(@PageableDefault Pageable pageable) {
        Page<UserResponseDTO> users = userRepository.findAll(pageable).map(UserResponseDTO::new);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateUser(@PathVariable @Valid Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return null;
//        User user = userService.updateUser(userUpdateDTO, id);
//        return ResponseEntity.ok(new UserResponseDTO)
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable @Valid Long id) {
        return null;
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
    }
}

