package com.luisbarrichello.api.ecommerce.service.order;

import com.luisbarrichello.api.ecommerce.dto.orderItem.OrderItemCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.order.OrderCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.order.OrderResponseDTO;
import com.luisbarrichello.api.ecommerce.model.order.Order;
import com.luisbarrichello.api.ecommerce.model.order.OrderStatus;
import com.luisbarrichello.api.ecommerce.model.orderItem.OrderItem;
import com.luisbarrichello.api.ecommerce.model.paymentMethod.PaymentMethod;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.order.OrderRepository;
import com.luisbarrichello.api.ecommerce.repository.orderItem.OrderItemRepository;
import com.luisbarrichello.api.ecommerce.repository.paymentMethodRepository.PaymentMethodRepository;
import com.luisbarrichello.api.ecommerce.repository.product.ProductRepository;
import com.luisbarrichello.api.ecommerce.repository.user.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        // Lógica para atualizar status do pedido
        return null;
    }

    public Order getOrder(Long userId, Long orderId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .filter(order1 -> order1.getId().equals(orderId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Page<OrderResponseDTO> getAllOrdersByUser(Pageable pageable, Long userId) {
        Page<Order> ordersPage = orderRepository.findByUserId(userId, pageable);
        return ordersPage.map(OrderResponseDTO::new);
    }

    public Order createOrder(OrderCreateDTO orderCreateDTO) {
        User user = userRepository.findById(orderCreateDTO.userId())
                .orElseThrow(() -> new ValidationException("User not found!"));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(orderCreateDTO.paymentMethodId())
                .orElseThrow(() -> new ValidationException("Payment method not found!"));

        Order order = new Order(orderCreateDTO, user, paymentMethod);
        setOrderItems(order, orderCreateDTO);
        order.setPriceTotal(order.calculatePriceTotalFinal());
        orderRepository.save(order);
        return order;
    }

    public boolean checkAllProductsinStock(OrderCreateDTO orderCreateDTO) {
        for (var item : orderCreateDTO.productList()) {
            var productInStock = checkProductStock(item);
            if(!productInStock) {
                throw new ValidationException("Product " + item.productName() + " out of stock");
            }
        }
        return true;
    }

    public boolean checkProductStock(OrderItemCreateDTO item) {
        Product product = productRepository.getReferenceById(item.productId());
        return product.getStock() >= item.quantity();
    }

    public void setOrderItems(Order order, OrderCreateDTO orderCreateDTO) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemCreateDTO itemDTO : orderCreateDTO.productList()) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new ValidationException("Product not found!"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductName(itemDTO.productName());
            orderItem.setPriceAtPurchase(itemDTO.priceAtPurchase());
            orderItem.setQuantity(itemDTO.quantity());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
    }

    public void deleteOrder(Long userId, Long orderId) {
        Order order = getOrder(userId, orderId);
        orderRepository.deleteById(order.getId());
    }
}
