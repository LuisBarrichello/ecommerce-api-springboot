package com.luisbarrichello.api.ecommerce.controller;

import com.luisbarrichello.api.ecommerce.dto.order.OrderResponseDTO;
import com.luisbarrichello.api.ecommerce.model.order.Order;
import com.luisbarrichello.api.ecommerce.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("{userId}")
    public ResponseEntity<List<OrderResponseDTO>> listAllOrders(@PathVariable Long userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        List<OrderResponseDTO> dtos = orderList.stream()
                .map((Order order) -> new OrderResponseDTO(order))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{userId}/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        var order = orders.stream()
                .filter(order1 -> order1.getId() == orderId).findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return ResponseEntity.ok(new OrderResponseDTO(order));
    }

}
