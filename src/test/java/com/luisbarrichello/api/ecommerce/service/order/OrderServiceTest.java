package com.luisbarrichello.api.ecommerce.service.order;

import com.luisbarrichello.api.ecommerce.dto.order.OrderCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.orderItem.OrderItemCreateDTO;
import com.luisbarrichello.api.ecommerce.model.order.Order;
import com.luisbarrichello.api.ecommerce.model.order.OrderStatus;
import com.luisbarrichello.api.ecommerce.model.paymentMethod.PaymentMethod;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.model.user.User;
import com.luisbarrichello.api.ecommerce.repository.order.OrderRepository;
import com.luisbarrichello.api.ecommerce.repository.orderItem.OrderItemRepository;
import com.luisbarrichello.api.ecommerce.repository.paymentMethodRepository.PaymentMethodRepository;
import com.luisbarrichello.api.ecommerce.repository.product.ProductRepository;
import com.luisbarrichello.api.ecommerce.repository.user.UserRepository;
import com.luisbarrichello.api.ecommerce.util.builder.OrderBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.PaymentMethodBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.ProductBuilder;
import com.luisbarrichello.api.ecommerce.util.builder.UserBuilder;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock OrderRepository orderRepository;
    @Mock ProductRepository productRepository;
    @Mock UserRepository userRepository;
    @Mock PaymentMethodRepository paymentMethodRepository;
    @Mock OrderItemRepository orderItemRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    @DisplayName("Should return order when user and order exist")
    void shouldReturnOrder_whenUserAndOrderExist() {
        Order order = new OrderBuilder().build();

        when(orderRepository.findByUserId(order.getUser().getId()))
                .thenReturn(List.of(order));

        Order result = orderService.getOrder(order.getUser().getId(), order.getId());

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
    }

    @Test
    @DisplayName("Should throw RuntimeException when order not found for user")
    void shouldThrowRuntimeException_whenOrderNotFoundForUser() {
        when(orderRepository.findByUserId(1L)).thenReturn(List.of());

        assertThrows(RuntimeException.class,
                () -> orderService.getOrder(1L, 99L));
    }

    @Test
    @DisplayName("Should create order and save when data is valid")
    void shouldCreateOrder_whenDataIsValid() {
        User user = new UserBuilder().build();
        PaymentMethod paymentMethod = new PaymentMethodBuilder().build();
        Product product = new ProductBuilder().build();

        OrderItemCreateDTO itemDTO = new OrderItemCreateDTO(
                product.getId(), product.getName(),
                product.getPrice(), 2
        );
        OrderCreateDTO dto = buildOrderCreateDTO(user.getId(), paymentMethod.getId(), List.of(itemDTO));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(paymentMethodRepository.findById(paymentMethod.getId())).thenReturn(Optional.of(paymentMethod));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Order result = orderService.createOrder(dto);

        assertNotNull(result);
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(paymentMethod.getId(), result.getPaymentMethod().getId());
        assertEquals(1, result.getOrderItems().size());
        assertNotNull(result.getPriceTotal());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Should throw ValidationException when user not found")
    void shouldThrowValidationException_whenUserNotFound() {
        OrderCreateDTO dto = buildOrderCreateDTO(99L, 1L, List.of());

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class,
                () -> orderService.createOrder(dto));

        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw ValidationException when payment method not found")
    void shouldThrowValidationException_whenPaymentMethodNotFound() {
        User user = new UserBuilder().build();
        OrderCreateDTO dto = buildOrderCreateDTO(user.getId(), 99L, List.of());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(paymentMethodRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class,
                () -> orderService.createOrder(dto));

        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw ValidationException when product not found in order items")
    void shouldThrowValidationException_whenProductNotFoundInOrderItems() {
        User user = new UserBuilder().build();
        PaymentMethod paymentMethod = new PaymentMethodBuilder().build();
        OrderItemCreateDTO itemDTO = new OrderItemCreateDTO(
                99L, "Produto Inexistente", BigDecimal.TEN, 1
        );
        OrderCreateDTO dto = buildOrderCreateDTO(user.getId(), paymentMethod.getId(), List.of(itemDTO));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(paymentMethodRepository.findById(paymentMethod.getId())).thenReturn(Optional.of(paymentMethod));
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ValidationException.class,
                () -> orderService.createOrder(dto));
    }

    @Test
    @DisplayName("Should return true when stock is sufficient")
    void shouldReturnTrue_whenStockIsSufficient() {
        Product product = new ProductBuilder().withStock(10).build();
        OrderItemCreateDTO item = new OrderItemCreateDTO(
                product.getId(), product.getName(), product.getPrice(), 5
        );

        when(productRepository.getReferenceById(product.getId())).thenReturn(product);

        assertTrue(orderService.checkProductStock(item));
    }

    @Test
    @DisplayName("Should return false when stock is insufficient")
    void shouldReturnFalse_whenStockIsInsufficient() {
        Product product = new ProductBuilder().withStock(2).build();
        OrderItemCreateDTO item = new OrderItemCreateDTO(
                product.getId(), product.getName(), product.getPrice(), 10
        );

        when(productRepository.getReferenceById(product.getId())).thenReturn(product);

        assertFalse(orderService.checkProductStock(item));
    }

    @Test
    @DisplayName("Should return true when all products are in stock")
    void shouldReturnTrue_whenAllProductsInStock() {
        Product p1 = new ProductBuilder().withId(1L).withStock(10).build();
        Product p2 = new ProductBuilder().withId(2L).withStock(5).build();

        OrderItemCreateDTO item1 = new OrderItemCreateDTO(1L, p1.getName(), p1.getPrice(), 3);
        OrderItemCreateDTO item2 = new OrderItemCreateDTO(2L, p2.getName(), p2.getPrice(), 2);

        OrderCreateDTO dto = buildOrderCreateDTO(1L, 1L, List.of(item1, item2));

        when(productRepository.getReferenceById(1L)).thenReturn(p1);
        when(productRepository.getReferenceById(2L)).thenReturn(p2);

        assertTrue(orderService.checkAllProductsinStock(dto));
    }

    @Test
    @DisplayName("Should throw ValidationException when one product is out of stock")
    void shouldThrowValidationException_whenOneProductOutOfStock() {
        Product inStock = new ProductBuilder().withId(1L).withStock(10).build();
        Product outOfStock = new ProductBuilder().withId(2L).withStock(1).build();

        OrderItemCreateDTO item1 = new OrderItemCreateDTO(1L, inStock.getName(), inStock.getPrice(), 2);
        OrderItemCreateDTO item2 = new OrderItemCreateDTO(2L, outOfStock.getName(), outOfStock.getPrice(), 5);

        OrderCreateDTO dto = buildOrderCreateDTO(1L, 1L, List.of(item1, item2));

        when(productRepository.getReferenceById(1L)).thenReturn(inStock);
        when(productRepository.getReferenceById(2L)).thenReturn(outOfStock);

        assertThrows(ValidationException.class,
                () -> orderService.checkAllProductsinStock(dto));
    }

    @Test
    @DisplayName("Should delete order when it exists")
    void shouldDeleteOrder_whenOrderExists() {
        Order order = new OrderBuilder().build();

        when(orderRepository.findByUserId(order.getUser().getId()))
                .thenReturn(List.of(order));

        orderService.deleteOrder(order.getUser().getId(), order.getId());

        verify(orderRepository, times(1)).deleteById(order.getId());
    }

    @Test
    @DisplayName("Should throw RuntimeException when deleting non-existent order")
    void shouldThrowRuntimeException_whenDeletingNonExistentOrder() {
        when(orderRepository.findByUserId(1L)).thenReturn(List.of());

        assertThrows(RuntimeException.class,
                () -> orderService.deleteOrder(1L, 99L));

        verify(orderRepository, never()).deleteById(any());
    }

    private OrderCreateDTO buildOrderCreateDTO(Long userId, Long paymentMethodId,
                                               List<OrderItemCreateDTO> items) {
        return new OrderCreateDTO(
                userId, items, null,
                OrderStatus.PENDING_PAYMENT, paymentMethodId,
                "TRACK-001", BigDecimal.ZERO,
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(1),
                null,
                BigDecimal.valueOf(20),
                LocalDateTime.now()
        );
    }
}