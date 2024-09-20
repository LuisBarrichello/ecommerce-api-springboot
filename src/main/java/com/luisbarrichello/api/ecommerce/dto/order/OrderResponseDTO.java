package com.luisbarrichello.api.ecommerce.dto.order;

import com.luisbarrichello.api.ecommerce.model.order.Order;
import com.luisbarrichello.api.ecommerce.model.orderItem.OrderItem;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.Discount;
import com.luisbarrichello.api.ecommerce.model.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        User user,
        List<OrderItem> productList,
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
                order.getUser(),
                order.getOrderItems(),
                order.getDiscount(),
                order.getPriceTotal(),
                order.getPaymentMethod(),
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
