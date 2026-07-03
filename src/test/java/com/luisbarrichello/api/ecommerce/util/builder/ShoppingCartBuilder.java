package com.luisbarrichello.api.ecommerce.util.builder;

import com.luisbarrichello.api.ecommerce.model.cartItem.CartItem;
import com.luisbarrichello.api.ecommerce.model.paymentMethod.PaymentMethod;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCart;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCartStatus;
import com.luisbarrichello.api.ecommerce.model.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartBuilder {
    private Long id = 1L;
    private User user = new UserBuilder().build();
    private List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal priceTotalItems = BigDecimal.ZERO;
    private BigDecimal priceTotalFinal = BigDecimal.ZERO;
    private ShoppingCartStatus status = ShoppingCartStatus.ACTIVE;
    private PaymentMethod paymentMethod = null;

    public ShoppingCartBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ShoppingCartBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public ShoppingCartBuilder withItem(CartItem item) {
        this.cartItems.add(item);
        return this;
    }

    public ShoppingCartBuilder withStatus(ShoppingCartStatus status) {
        this.status = status;
        return this;
    }

    public ShoppingCart build() {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(this.id);
        cart.setUser(this.user);
        cart.setCartItems(this.cartItems);
        cart.setPriceTotalItems(this.priceTotalItems);
        cart.setPriceTotalFinal(this.priceTotalFinal);
        cart.setStatus(this.status);
        cart.setPaymentMethod(this.paymentMethod);
        return cart;
    }
}