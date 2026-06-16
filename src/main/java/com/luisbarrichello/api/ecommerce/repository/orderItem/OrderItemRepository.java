package com.luisbarrichello.api.ecommerce.repository.orderItem;

import com.luisbarrichello.api.ecommerce.model.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
