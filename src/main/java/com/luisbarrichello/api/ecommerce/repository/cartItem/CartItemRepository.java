package com.luisbarrichello.api.ecommerce.repository.cartItem;

import com.luisbarrichello.api.ecommerce.model.cartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
