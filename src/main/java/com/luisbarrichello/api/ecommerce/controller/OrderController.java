package com.luisbarrichello.api.ecommerce.controller;

import com.luisbarrichello.api.ecommerce.dto.order.OrderCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.order.OrderResponseDTO;
import com.luisbarrichello.api.ecommerce.model.order.Order;
import com.luisbarrichello.api.ecommerce.repository.order.OrderRepository;
import com.luisbarrichello.api.ecommerce.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderCreateDTO orderCreateDTO,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        Order order = orderService.createOrder(orderCreateDTO);
        URI uri = uriComponentsBuilder.path("orders/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.ok(new OrderResponseDTO(order));
    }

    @GetMapping("{userId}")
    public ResponseEntity<Page<OrderResponseDTO>> listAllOrders(@PageableDefault Pageable pageable, @PathVariable Long userId) {
        Page<OrderResponseDTO> orderResponseDTOs = orderService.getAllOrdersByUser(pageable, userId);
        return ResponseEntity.ok(orderResponseDTOs);
    }

    @GetMapping("{userId}/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        Order order = orderService.getOrder(userId, orderId);
        return ResponseEntity.ok(new OrderResponseDTO(order));
    }

    @DeleteMapping("{userId}/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        orderService.deleteOrder(userId, orderId);
        return ResponseEntity.noContent().build();
    }
}
