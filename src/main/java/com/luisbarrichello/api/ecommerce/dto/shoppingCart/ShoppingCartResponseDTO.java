package com.luisbarrichello.api.ecommerce.dto.shoppingCart;

import com.luisbarrichello.api.ecommerce.dto.cartItem.CartItemResponseDTO;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.Discount;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCart;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCartStatus;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingCartResponseDTO(
        Long id,
        BigDecimal priceTotalItems,
        Discount discount,
        BigDecimal taxes,
        BigDecimal shipping,
        ShoppingCartStatus status,
        String paymentMethod,
        BigDecimal priceTotalFinal,
        List<CartItemResponseDTO> cartItems
) {
    public ShoppingCartResponseDTO(ShoppingCart shoppingCart) {
        this(shoppingCart.getId(),
                shoppingCart.getPriceTotalItems(),
                shoppingCart.getDiscount(),
                shoppingCart.getTaxes(),
                shoppingCart.getShipping(),
                shoppingCart.getStatus(),
                shoppingCart.getPaymentMethod(),
                shoppingCart.getPriceTotalFinal(),
                shoppingCart.getCartItems()
                        .stream()
                        .map(cartItem -> new CartItemResponseDTO(cartItem))
                        .toList()
        );
    }
}
