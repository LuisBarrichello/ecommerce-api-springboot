package com.luisbarrichello.api.ecommerce.service.order;

import com.luisbarrichello.api.ecommerce.dto.order.OrderCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.orderItem.OrderItemCreateDTO;
import com.luisbarrichello.api.ecommerce.model.order.Order;
import com.luisbarrichello.api.ecommerce.model.order.OrderStatus;
import com.luisbarrichello.api.ecommerce.model.paymentMethod.PaymentMethod;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.order.OrderRepository;
import com.luisbarrichello.api.ecommerce.repository.paymentMethodRepository.PaymentMethodRepository;
import com.luisbarrichello.api.ecommerce.repository.product.ProductRepository;
import com.luisbarrichello.api.ecommerce.repository.user.UserRepository;
import com.luisbarrichello.api.ecommerce.util.builder.OrderBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.PaymentMethodBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.ProductBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    void updateOrderStatus() {
    }

    @Test
    @DisplayName("Should return success when retrieving an existing Order")
    void getOrderSuccess() {
        Order order = new OrderBuilder().build();

        when(orderRepository.findByUserId(order.getUser().getId())).thenReturn(List.of(order));

        Order result = orderService.getOrder(order.getUser().getId(), order.getId());

        assertNotNull(result);
        Assertions.assertEquals(order.getId(), result.getId());
    }


    @Test
    void createOrderSuccess() {
        User user = new UserBuilder().build();
        PaymentMethod paymentMethod = new PaymentMethodBuilder().build();
        Product product = new ProductBuilder().build();

        OrderItemCreateDTO orderItemDTO = new OrderItemCreateDTO(
                product.getId(), "Celular", BigDecimal.valueOf(1500), 2
        );

        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(
                user.getId(),
                List.of(orderItemDTO),
                null,
                OrderStatus.PENDING_PAYMENT,
                paymentMethod.getId(),
                "TRACK-1234",
                BigDecimal.ZERO, // taxes
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(1),
                null,
                BigDecimal.valueOf(20),
                LocalDateTime.now()
        );

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(paymentMethodRepository.findById(paymentMethod.getId())).thenReturn(Optional.of(paymentMethod));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Order result = orderService.createOrder(orderCreateDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUser(), user.getId());
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any(Order.class));
    }

    @Test
    void checkAllProductsInStock() {
    }

    @Test
    void checkProductStock() {
        OrderItemCreateDTO itemDTO = new OrderItemCreateDTO(
                1L, "Celular", BigDecimal.valueOf(1500), 5
        );
        Product productInDatabase = new ProductBuilder()
                .withId(1L)
                .withStock(10)
                .build();
        when(productRepository.getReferenceById(itemDTO.productId()))
                .thenReturn(productInDatabase);
        boolean result = orderService.checkProductStock(itemDTO);
        assertTrue(result);
    }

    @Test
    void deleteOrder() {
        Order order = new OrderBuilder().build();
        Long userId = order.getUser().getId();
        Long orderId = order.getId();

        when(orderRepository.findByUserId(userId)).thenReturn(List.of(order));

        orderService.deleteOrder(userId, order.getId());

        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(orderId);
    }
}