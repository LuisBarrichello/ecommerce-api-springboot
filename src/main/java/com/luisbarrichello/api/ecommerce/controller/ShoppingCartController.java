package com.luisbarrichello.api.ecommerce.controller;

import com.luisbarrichello.api.ecommerce.dto.cartItem.CartItemUpdateDTO;
import com.luisbarrichello.api.ecommerce.dto.shoppingCart.ShoppingCartResponseDTO;
import com.luisbarrichello.api.ecommerce.dto.cartItem.CartItemCreateDTO;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCart;
import com.luisbarrichello.api.ecommerce.repository.shoppingCart.ShoppingCartRepository;
import com.luisbarrichello.api.ecommerce.service.shoppingCart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartResponseDTO> getShoppingCart(@PathVariable Long userId) {
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByUserid(userId);
        return ResponseEntity.ok(new ShoppingCartResponseDTO(shoppingCart));
    }

    @PostMapping("/{userId}/add-item")
    public ResponseEntity<ShoppingCartResponseDTO> addItemToCart(@PathVariable Long userId, @RequestBody CartItemCreateDTO cartItemCreateDTO) {
        ShoppingCart shoppingCartWithNewItem = shoppingCartService.addItemToCart(userId, cartItemCreateDTO);
        return ResponseEntity.ok(new ShoppingCartResponseDTO(shoppingCartWithNewItem));
    }

    @PutMapping("/{userId}/update-item")
    public ResponseEntity<ShoppingCartResponseDTO> updateItemQuantity(@PathVariable Long userId, @RequestBody CartItemUpdateDTO cartItemUpdateDTO) {
        ShoppingCart updateItemQuantity = shoppingCartService.updateItemQuantity(userId, cartItemUpdateDTO);
        return ResponseEntity.ok( new ShoppingCartResponseDTO(updateItemQuantity));
    }

    @DeleteMapping("/{userId}/remove-item/{itemId}")
    public ResponseEntity<ShoppingCartResponseDTO> removeItemFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        ShoppingCart removeItemFromCart = shoppingCartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok(new ShoppingCartResponseDTO(removeItemFromCart));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ShoppingCartResponseDTO> clearCart(@PathVariable Long userId) {
        ShoppingCart clearedCart = shoppingCartService.clearCart(userId);
        return ResponseEntity.ok(new ShoppingCartResponseDTO(clearedCart));
    }
}
