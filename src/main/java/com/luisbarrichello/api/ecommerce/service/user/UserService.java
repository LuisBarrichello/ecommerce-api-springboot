package com.luisbarrichello.api.ecommerce.service.user;

import com.luisbarrichello.api.ecommerce.dto.user.UserCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.user.UserUpdateDTO;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCart;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.shoppingCart.ShoppingCartRepository;
import com.luisbarrichello.api.ecommerce.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    public User createUser(UserCreateDTO userCreateDTO) {
        User user = new User(userCreateDTO);
        checkDuplicityOfEmail(user);
        getShoppingCart(user);
        userRepository.save(user);
        return user;
    }

    private boolean checkDuplicityOfEmail(User user) {
        var emailExistent = userRepository.existsByEmail(user.getEmail());
        if(emailExistent) throw new ValidationException("Email já cadastrado, faça login para acessar conta.");
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

    public void getShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);

        user.setShoppingCart(shoppingCart);
    }
}
