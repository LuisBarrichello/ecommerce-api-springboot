package com.luisbarrichello.api.ecommerce.model.order;

import com.luisbarrichello.api.ecommerce.dto.order.OrderCreateDTO;
import com.luisbarrichello.api.ecommerce.model.orderItem.OrderItem;
import com.luisbarrichello.api.ecommerce.model.paymentMethod.PaymentMethod;
import com.luisbarrichello.api.ecommerce.model.shoppingCart.Discount;
import com.luisbarrichello.api.ecommerce.model.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private BigDecimal priceTotal;

    @ManyToOne
    @JoinColumn(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(unique = true)
    private String trackingCode;

    private BigDecimal taxes;
    private LocalDateTime deliveryDate;
    private LocalDateTime returnDeadline;
    private LocalDateTime dispatchDate;
    private LocalDateTime dateOfReceipt;
    private BigDecimal freightPrice;
    private LocalDateTime createdAt;

    public Order(OrderCreateDTO orderCreateDTO, User user, PaymentMethod paymentMethod) {
        this.user = user;
        this.discount = orderCreateDTO.discount();
        this.status = orderCreateDTO.status();
        this.paymentMethod = paymentMethod;
        this.trackingCode = orderCreateDTO.trackingCode();;
        this.taxes = orderCreateDTO.taxes();
        this.deliveryDate = orderCreateDTO.deliveryDate();
        this.returnDeadline = orderCreateDTO.returnDeadline();
        this.dispatchDate = orderCreateDTO.dispatchDate();
        this.dateOfReceipt = orderCreateDTO.dateOfReceipt();
        this.createdAt = orderCreateDTO.createdAt();
        this.freightPrice = orderCreateDTO.freightPrice();
    }

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

    public PaymentMethod getPaymentMethod() {
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

    public BigDecimal calculatePriceTotalItems() {
        BigDecimal total = orderItems.stream()
                .map(orderItem -> orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        return total;
    }

    public BigDecimal calculatePriceTotalFinal() {
        BigDecimal total = calculatePriceTotalItems();

        if (taxes != null) {
            total = total.subtract(taxes);
        }
        if (freightPrice != null) {
            total = total.add(freightPrice);
        }
        if (discount != null) {
            total = total.subtract(discount.getDiscountAmount());
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public void recalculateTotals () {
        priceTotal = calculatePriceTotalItems();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setFreightPrice(BigDecimal freightPrice) {
        this.freightPrice = freightPrice;
    }

    public void setDateOfReceipt(LocalDateTime dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public void setDispatchDate(LocalDateTime dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public void setReturnDeadline(LocalDateTime returnDeadline) {
        this.returnDeadline = returnDeadline;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
