package com.luisbarrichello.api.ecommerce.service.user;

import com.luisbarrichello.api.ecommerce.dto.user.UserCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.user.UserUpdateDTO;
import com.luisbarrichello.api.ecommerce.model.role.Role;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCart;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.role.RoleRepository;
import com.luisbarrichello.api.ecommerce.repository.shoppingCart.ShoppingCartRepository;
import com.luisbarrichello.api.ecommerce.repository.user.UserRepository;
import com.luisbarrichello.api.ecommerce.service.shoppingCart.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Transactional
    public User createUser(UserCreateDTO userCreateDTO) {
        checkDuplicityOfEmail(userCreateDTO.email());
        Role role = getRole(userCreateDTO.roleId());

        String encryptedPassword = passwordEncoder.encode(userCreateDTO.password());
        User user = new User(userCreateDTO, role);
        user.setPassword(encryptedPassword);

        userRepository.save(user);
        shoppingCartService.createShoppingCart(user);
        return user;
    }

    private boolean checkDuplicityOfEmail(String email) {
        var emailExistent = userRepository.existsByEmail(email);
        if(emailExistent) throw new ValidationException("Email already registered, please log in to access your account");
        return false;
    }

    public User updateUser(UserUpdateDTO userUpdateDTO, Long id) {
        User user = userRepository.getReferenceById(id);

        if (userUpdateDTO.name() != null) user.setName(userUpdateDTO.name());
        if (userUpdateDTO.phoneNumber() != null) user.setPhoneNumber(userUpdateDTO.phoneNumber());
        if (userUpdateDTO.username() != null) user.setUsername(userUpdateDTO.username());

        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long id) {
        var user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }
    }

    public Role getRole(Long roleId) {
        Role role = roleRepository.getReferenceById(roleId);
        return role;
    }
}
