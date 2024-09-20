package com.luisbarrichello.api.ecommerce.model.orderItem;

import com.luisbarrichello.api.ecommerce.model.order.Order;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "orderItem")
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String productName;
    private BigDecimal priceAtPurchase;
    private Integer quantity;


    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
}
