package com.luisbarrichello.api.ecommerce.repository.shoppingCart;


import com.luisbarrichello.api.ecommerce.model.shoppingCart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}

