package com.luisbarrichello.api.ecommerce.dto.order;

import com.luisbarrichello.api.ecommerce.dto.orderItem.OrderItemCreateDTO;
import com.luisbarrichello.api.ecommerce.model.order.OrderStatus;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.Discount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreateDTO(
        Long userId,
        List<OrderItemCreateDTO> productList,
        Discount discount,
        OrderStatus status,
        Long paymentMethodId,
        String trackingCode,
        BigDecimal taxes,
        LocalDateTime deliveryDate,
        LocalDateTime returnDeadline,
        LocalDateTime dispatchDate,
        LocalDateTime dateOfReceipt,
        BigDecimal freightPrice,
        LocalDateTime createdAt
) {

}
