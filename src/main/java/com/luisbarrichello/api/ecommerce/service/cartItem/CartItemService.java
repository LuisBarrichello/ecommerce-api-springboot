package com.luisbarrichello.api.ecommerce.service.cartItem;

import com.luisbarrichello.api.ecommerce.dto.cartItem.CartItemCreateDTO;
import com.luisbarrichello.api.ecommerce.model.cartItem.CartItem;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.repository.cartItem.CartItemRepository;
import com.luisbarrichello.api.ecommerce.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartItemService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public CartItem createItem(CartItemCreateDTO cartItemCreateDTO) {
        Product product = productRepository.findById(cartItemCreateDTO.productId())
        .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemCreateDTO.quantity());
        cartItem.setPrice(calculatePriceTotalTheEqualItems(product, cartItemCreateDTO));

        cartItemRepository.save(cartItem);

        return cartItem;
    }


    public BigDecimal calculatePriceTotalTheEqualItems(Product product, CartItemCreateDTO cartItemCreateDTO) {
        BigDecimal total = product
                .getPrice()
                .multiply(BigDecimal
                        .valueOf(cartItemCreateDTO.quantity()));
        return total;
    }
}
