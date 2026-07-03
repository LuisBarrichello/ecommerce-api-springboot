package com.luisbarrichello.api.ecommerce.service.shoppingCart;

import com.luisbarrichello.api.ecommerce.dto.cartItem.CartItemCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.cartItem.CartItemUpdateDTO;
import com.luisbarrichello.api.ecommerce.model.cartItem.CartItem;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCart;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.cartItem.CartItemRepository;
import com.luisbarrichello.api.ecommerce.repository.shoppingCart.ShoppingCartRepository;
import com.luisbarrichello.api.ecommerce.repository.user.UserRepository;
import com.luisbarrichello.api.ecommerce.service.cartItem.CartItemService;
import com.luisbarrichello.api.ecommerce.util.builder.CartItemBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.ProductBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.ShoppingCartBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.UserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private ShoppingCartRepository shoppingCartRepository;
    @Mock private CartItemService cartItemService;
    @Mock private CartItemRepository cartItemRepository;

    @InjectMocks
    ShoppingCartService shoppingCartService;

    @Test
    @DisplayName("Should create shopping cart and associate to user")
    void shouldCreateShoppingCart_andAssociateToUser() {
        User user = new UserBuilder().build();

        shoppingCartService.createShoppingCart(user);

        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
        assertNotNull(user.getShoppingCart());
    }

    @Test
    @DisplayName("Should add new item when product not in cart")
    void shouldAddNewItem_whenProductNotInCart() {
        User user = new UserBuilder().build();
        ShoppingCart cart = new ShoppingCartBuilder().build();
        user.setShoppingCart(cart);

        CartItemCreateDTO dto = new CartItemCreateDTO(1L, 3);
        CartItem newItem = new CartItemBuilder().withQuantity(3).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(cartItemService.createItem(dto)).thenReturn(newItem);

        ShoppingCart result = shoppingCartService.addItemToCart(user.getId(), dto);

        assertEquals(1, result.getCartItems().size());
        assertEquals(3, result.getCartItems().getFirst().getQuantity());
        verify(shoppingCartRepository, times(1)).save(result);
    }

    @Test
    @DisplayName("Should sum quantity when product already in cart")
    void shouldSumQuantity_whenProductAlreadyInCart() {
        CartItem existingItem = new CartItemBuilder()
                .withProduct(new ProductBuilder().withId(1L).build())
                .withQuantity(2)
                .build();

        ShoppingCart cart = new ShoppingCartBuilder().withItem(existingItem).build();
        User user = new UserBuilder().build();
        user.setShoppingCart(cart);

        CartItemCreateDTO dto = new CartItemCreateDTO(1L, 3);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ShoppingCart result = shoppingCartService.addItemToCart(user.getId(), dto);

        assertEquals(1, result.getCartItems().size());
        assertEquals(5, result.getCartItems().getFirst().getQuantity());
        verify(cartItemService, never()).createItem(any());
    }

    @Test
    @DisplayName("Should throw RuntimeException when user not found on addItem")
    void shouldThrowRuntimeException_whenUserNotFoundOnAddItem() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> shoppingCartService.addItemToCart(99L, new CartItemCreateDTO(1L, 1)));
    }

    @Test
    @DisplayName("Should update item quantity when item exists in cart")
    void shouldUpdateItemQuantity_whenItemExistsInCart() {
        CartItem item = new CartItemBuilder().withId(1L).withQuantity(2).build();
        ShoppingCart cart = new ShoppingCartBuilder().withItem(item).build();
        User user = new UserBuilder().build();
        user.setShoppingCart(cart);

        CartItemUpdateDTO dto = new CartItemUpdateDTO(1L, 5);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ShoppingCart result = shoppingCartService.updateItemQuantity(user.getId(), dto);

        assertEquals(5, result.getCartItems().getFirst().getQuantity());
        verify(shoppingCartRepository, times(1)).save(result);
    }

    @Test
    @DisplayName("Should remove item when quantity updated to zero")
    void shouldRemoveItem_whenQuantityUpdatedToZero() {
        CartItem item = new CartItemBuilder().withId(1L).withQuantity(3).build();
        ShoppingCart cart = new ShoppingCartBuilder().withItem(item).build();
        User user = new UserBuilder().build();
        user.setShoppingCart(cart);

        CartItemUpdateDTO dto = new CartItemUpdateDTO(1L, 0);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ShoppingCart result = shoppingCartService.updateItemQuantity(user.getId(), dto);

        assertEquals(0, result.getCartItems().size());
    }

    @Test
    @DisplayName("Should throw RuntimeException when item not found in cart")
    void shouldThrowRuntimeException_whenItemNotFoundInCart() {
        ShoppingCart cart = new ShoppingCartBuilder().build();
        User user = new UserBuilder().build();
        user.setShoppingCart(cart);

        CartItemUpdateDTO dto = new CartItemUpdateDTO(99L, 2);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class,
                () -> shoppingCartService.updateItemQuantity(user.getId(), dto));
    }

    @Test
    @DisplayName("Should remove item from cart when item exists")
    void shouldRemoveItem_whenItemExistsInCart() {
        CartItem item = new CartItemBuilder().withId(1L).build();
        ShoppingCart cart = new ShoppingCartBuilder().withItem(item).build();
        User user = new UserBuilder().build();
        user.setShoppingCart(cart);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ShoppingCart result = shoppingCartService.removeItemFromCart(user.getId(), 1L);

        assertEquals(0, result.getCartItems().size());
        verify(cartItemRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw RuntimeException when removing non-existent item")
    void shouldThrowRuntimeException_whenRemovingNonExistentItem() {
        ShoppingCart cart = new ShoppingCartBuilder().build();
        User user = new UserBuilder().build();
        user.setShoppingCart(cart);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class,
                () -> shoppingCartService.removeItemFromCart(user.getId(), 99L));
    }


    @Test
    @DisplayName("Should clear all items from cart")
    void shouldClearAllItems_whenCartHasItems() {
        CartItem item1 = new CartItemBuilder().withId(1L).build();
        CartItem item2 = new CartItemBuilder().withId(2L).build();
        ShoppingCart cart = new ShoppingCartBuilder().withItem(item1).withItem(item2).build();
        User user = new UserBuilder().build();
        user.setShoppingCart(cart);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ShoppingCart result = shoppingCartService.clearCart(user.getId());

        assertEquals(0, result.getCartItems().size());
        verify(cartItemRepository, times(1)).deleteAll(any());
        verify(shoppingCartRepository, times(1)).save(result);
    }

    @Test
    @DisplayName("Should do nothing when clearing empty cart")
    void shouldDoNothing_whenClearingEmptyCart() {
        ShoppingCart cart = new ShoppingCartBuilder().build();
        User user = new UserBuilder().build();
        user.setShoppingCart(cart);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ShoppingCart result = shoppingCartService.clearCart(user.getId());

        assertEquals(0, result.getCartItems().size());
        verify(cartItemRepository, never()).deleteAll(any());
    }
}