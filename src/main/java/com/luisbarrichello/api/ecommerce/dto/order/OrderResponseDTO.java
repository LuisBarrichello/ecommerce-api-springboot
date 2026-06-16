package com.luisbarrichello.api.ecommerce.dto.order;

import com.luisbarrichello.api.ecommerce.dto.orderItem.OrderItemCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.user.UserResponseDTO;
import com.luisbarrichello.api.ecommerce.model.order.Order;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.Discount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        UserResponseDTO user,
        List<OrderItemCreateDTO.OrderItemResponseDTO> productList,
        Discount discount,
        BigDecimal priceTotal,
        String paymentMethod,
        String trackingCode,
        BigDecimal taxes,
        LocalDateTime deliveryDate,
        LocalDateTime returnDeadline,
        LocalDateTime dispatchDate,
        LocalDateTime dateOfReceipt,
        BigDecimal freightPrice,
        LocalDateTime createdAt
) {
    public OrderResponseDTO(Order order) {
        this(
                order.getId(),
                new UserResponseDTO(order.getUser()),
                order.getOrderItems().stream().map(OrderItemCreateDTO.OrderItemResponseDTO::new).toList(),
                order.getDiscount(),
                order.getPriceTotal(),
                order.getPaymentMethod().toString(),
                order.getTrackingCode(),
                order.getTaxes(),
                order.getDeliveryDate(),
                order.getReturnDeadline(),
                order.getDispatchDate(),
                order.getDateOfReceipt(),
                order.getFreightPrice(),
                order.getCreatedAt()
        );
    }
}
