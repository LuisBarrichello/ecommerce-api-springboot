package com.luisbarrichello.api.ecommerce.util.builder;

import com.luisbarrichello.api.ecommerce.model.order.Order;
import com.luisbarrichello.api.ecommerce.model.order.OrderStatus;
import com.luisbarrichello.api.ecommerce.model.orderItem.OrderItem;
import com.luisbarrichello.api.ecommerce.model.paymentMethod.PaymentMethod;
import com.luisbarrichello.api.ecommerce.model.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private Long id = 1L;
    private User user = new UserBuilder().build();
    private List<OrderItem> orderItems = new ArrayList<>(List.of(new OrderItemBuilder().build()));
    private OrderStatus status = OrderStatus.PENDING_PAYMENT;
    private BigDecimal priceTotal = BigDecimal.valueOf(150.0);
    private PaymentMethod paymentMethod = new PaymentMethodBuilder().build();
    private String trackingCode = "TRACK123456";
    private BigDecimal freightPrice = BigDecimal.valueOf(20.0);

    public OrderBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public OrderBuilder withItem(OrderItem item) {
        this.orderItems.add(item);
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setId(this.id);
        order.setUser(this.user);
        order.setOrderItems(this.orderItems);
        order.setStatus(this.status);
        order.setPriceTotal(this.priceTotal);
        order.setPaymentMethod(this.paymentMethod);
        order.setTrackingCode(this.trackingCode);
        order.setFreightPrice(this.freightPrice);
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }
}