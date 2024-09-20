package com.luisbarrichello.api.ecommerce.model.order;

import com.luisbarrichello.api.ecommerce.model.orderItem.OrderItem;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.Discount;
import com.luisbarrichello.api.ecommerce.model.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "order")
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    private OrderStatus status;

    private BigDecimal priceTotal;

    private String paymentMethod;

    private String trackingCode;

    private BigDecimal taxes;

    private LocalDateTime deliveryDate;

    private LocalDateTime returnDeadline;

    private LocalDateTime dispatchDate;

    private LocalDateTime dateOfReceipt;

    private BigDecimal freightPrice;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreated() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Discount getDiscount() {
        return discount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public LocalDateTime getReturnDeadline() {
        return returnDeadline;
    }

    public LocalDateTime getDispatchDate() {
        return dispatchDate;
    }

    public LocalDateTime getDateOfReceipt() {
        return dateOfReceipt;
    }

    public BigDecimal getFreightPrice() {
        return freightPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
