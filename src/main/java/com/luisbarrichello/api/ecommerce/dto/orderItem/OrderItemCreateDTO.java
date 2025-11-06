package com.luisbarrichello.api.ecommerce.dto.orderItem;

import com.luisbarrichello.api.ecommerce.model.orderItem.OrderItem;

import java.math.BigDecimal;

public record OrderItemCreateDTO(
        Long productId,
        String productName,
        BigDecimal priceAtPurchase,
        Integer quantity
) {
    public static record OrderItemResponseDTO(
            Long productId,
            String productName,
            int quantity,
            BigDecimal priceAtPurchase
    ) {
        public OrderItemResponseDTO(OrderItem orderItem) {
            this(
                    orderItem.getId(),
                    orderItem.getProductName(),
                    orderItem.getQuantity(),
                    orderItem.getPriceAtPurchase()
            );
        }
    }
}
