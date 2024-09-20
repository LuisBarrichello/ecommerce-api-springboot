package com.luisbarrichello.api.ecommerce.service.shoppingCart;

import com.luisbarrichello.api.ecommerce.dto.cartItem.CartItemCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.cartItem.CartItemResponseDTO;
import com.luisbarrichello.api.ecommerce.dto.cartItem.CartItemUpdateDTO;
import com.luisbarrichello.api.ecommerce.model.cartItem.CartItem;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCart;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCartStatus;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.cartItem.CartItemRepository;
import com.luisbarrichello.api.ecommerce.repository.shoppingCart.ShoppingCartRepository;
import com.luisbarrichello.api.ecommerce.repository.user.UserRepository;
import com.luisbarrichello.api.ecommerce.service.cartItem.CartItemService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShoppingCartService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemRepository cartItemRepository;

    public ShoppingCart findShoppingCartByUserid(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));;
        ShoppingCart shoppingCart = user.getShoppingCart();

        if (shoppingCart == null) {
            throw new ValidationException("Shopping cart not found for user " + userId);
        }

        return shoppingCart;
    }

    public ShoppingCart addItemToCart(Long userId, CartItemCreateDTO cartItemCreateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart shoppingCart = user.getShoppingCart();

        CartItem existingItem = verifyExistingItemInShoppingCart(shoppingCart, cartItemCreateDTO);

        if (existingItem != null) {
            existingItem.setQuantity(cartItemCreateDTO.quantity() + existingItem.getQuantity());
        } else {
            CartItem newCartItem = cartItemService.createItem(cartItemCreateDTO);

            shoppingCart.getCartItems().add(newCartItem);
            newCartItem.setShoppingCart(shoppingCart);
        }

        shoppingCart.recalculateTotals();

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }

    public ShoppingCart updateItemQuantity(Long userId, CartItemUpdateDTO cartItemUpdateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCart shoppingCart = user.getShoppingCart();
        List<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem itemToUpdate = cartItems.stream()
                .filter(cartItem -> cartItem.getId().equals(cartItemUpdateDTO.id()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in the cart"));


        int newQuantity = itemToUpdate.getQuantity() + cartItemUpdateDTO.quantity();
        if (newQuantity <= 0) {
            cartItems.remove(itemToUpdate);
        } else {
            itemToUpdate.setQuantity(newQuantity);
        }

        shoppingCart.recalculateTotals();
        cartItemRepository.save(itemToUpdate);


        return shoppingCart;
    }

    public ShoppingCart removeItemFromCart(Long userId, Long itemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<CartItem> cartItems = shoppingCart.getCartItems();

        CartItem cartItem = cartItems.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in the cart"));

        cartItems.remove(cartItem);
        cartItemRepository.deleteById(cartItem.getId());

        shoppingCart.recalculateTotals();

        return shoppingCart;
    }

    public ShoppingCart clearCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItems = shoppingCart.getCartItems();

        if (!cartItems.isEmpty()) {
            cartItemRepository.deleteAll(cartItems);
            cartItems.clear();
        }

        shoppingCart.recalculateTotals();;
        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }

    public CartItem verifyExistingItemInShoppingCart(ShoppingCart shoppingCart, CartItemCreateDTO cartItemCreateDTO) {
        CartItem existingItem = shoppingCart.getCartItems()
                .stream()
                .filter((cartItem -> cartItem.getProduct().getId().equals(cartItemCreateDTO.productId()))
                ).findFirst().orElse(null);
        return existingItem;
    }

//    public searchItemInShoppingCart(Long userId, Object dto) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        ShoppingCart shoppingCart = user.getShoppingCart();
//        List<CartItem> cartItems = shoppingCart.getCartItems();
//        CartItem item = cartItems.stream()
//                .filter(cartItem -> cartItem.getId().equals(dto.id()))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Item not found in the cart"));
//
//        return item;
//    }
}
