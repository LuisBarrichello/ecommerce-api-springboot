package com.luisbarrichello.api.ecommerce.util.builder;

import com.luisbarrichello.api.ecommerce.model.cartItem.CartItem;
import com.luisbarrichello.api.ecommerce.model.product.Product;

import java.math.BigDecimal;

public class CartItemBuilder {
    private Long id = 1L;
    private Product product = new ProductBuilder().build();
    private Integer quantity = 1;
    private BigDecimal price = BigDecimal.valueOf(150.0);

    public CartItemBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CartItemBuilder withProduct(Product product) {
        this.product = product;
        this.price = product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
        return this;
    }

    public CartItemBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        this.price = this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
        return this;
    }

    public CartItem build() {
        CartItem cartItem = new CartItem();
        cartItem.setId(this.id);
        cartItem.setProduct(this.product);
        cartItem.setQuantity(this.quantity);
        cartItem.setPrice(this.price);
        return cartItem;
    }
}