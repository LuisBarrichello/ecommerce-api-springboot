package com.luisbarrichello.api.ecommerce.repository.user;

import com.luisbarrichello.api.ecommerce.model.role.Role;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.role.RoleRepository;
import com.luisbarrichello.api.ecommerce.util.builder.RoleBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    private Role savedRole;

    @BeforeEach
    void setUp() {
        Role role = new RoleBuilder().withName("CUSTOMER").build();
        role.setId(null);
        savedRole = roleRepository.save(role);
    }

    @Test
    @DisplayName("Should return true when email exists")
    void shouldReturnTrue_whenEmailExists() {
        User user = new UserBuilder()
                .withId(null)
                .withEmail("test@email.com")
                .withRole(savedRole)
                .build();
        userRepository.save(user);

        assertTrue(userRepository.existsByEmail("test@email.com"));
    }

    @Test
    @DisplayName("Should return false when email does not exist")
    void shouldReturnFalse_whenEmailDoesNotExist() {
        assertFalse(userRepository.existsByEmail("naoexiste@email.com"));
    }

    @Test
    @DisplayName("Should find user by email")
    void shouldFindUser_whenSearchingByEmail() {
        User user = new UserBuilder()
                .withId(null)
                .withEmail("find@email.com")
                .withRole(savedRole)
                .build();
        userRepository.save(user);

        Optional<User> result = userRepository.findByEmail("find@email.com");

        assertTrue(result.isPresent());
        assertEquals("find@email.com", result.get().getEmail());
    }

    @Test
    @DisplayName("Should return empty when email not found")
    void shouldReturnEmpty_whenEmailNotFound() {
        Optional<User> result = userRepository.findByEmail("ghost@email.com");

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should find user by username")
    void shouldFindUser_whenSearchingByUsername() {
        User user = new UserBuilder()
                .withId(null)
                .withEmail("user@email.com")
                .withUsername("TESTUSER")
                .withRole(savedRole)
                .build();
        userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("TESTUSER");

        assertTrue(result.isPresent());
        assertEquals("TESTUSER", result.get().getUsername());
    }

    @Test
    @DisplayName("Should persist user with all mandatory fields")
    void shouldPersistUser_withAllMandatoryFields() {
        User user = new UserBuilder()
                .withId(null)
                .withEmail("persist@email.com")
                .withRole(savedRole)
                .build();

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals("persist@email.com", saved.getEmail());
        assertEquals(savedRole.getId(), saved.getRole().getId());
    }
}