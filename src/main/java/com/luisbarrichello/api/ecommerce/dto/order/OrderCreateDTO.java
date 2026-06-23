package com.luisbarrichello.api.ecommerce.dto.order;

import com.luisbarrichello.api.ecommerce.dto.orderItem.OrderItemCreateDTO;
import com.luisbarrichello.api.ecommerce.model.order.OrderStatus;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.Discount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreateDTO(
        @NotNull(message = "User ID is mandatory")
        Long userId,

        @NotNull(message = "Product list cannot be null")
        @NotEmpty(message = "Order must have at least one item")
        List<OrderItemCreateDTO> productList,

        Discount discount,

        @NotNull(message = "Order status is mandatory")
        OrderStatus status,

        @NotNull(message = "Payment method is mandatory")
        Long paymentMethodId,

        @NotBlank(message = "Tracking code is mandatory")
        String trackingCode,

        @NotNull
        BigDecimal taxes,
        LocalDateTime deliveryDate,
        LocalDateTime returnDeadline,
        LocalDateTime dispatchDate,
        LocalDateTime dateOfReceipt,
        BigDecimal freightPrice,
        LocalDateTime createdAt
) {

}
