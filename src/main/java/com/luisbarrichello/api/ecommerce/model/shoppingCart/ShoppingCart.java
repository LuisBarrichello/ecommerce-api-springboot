package com.luisbarrichello.api.ecommerce.model.shoppingCart;

import com.luisbarrichello.api.ecommerce.model.cartItem.CartItem;
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

@Entity(name = "ShoppingCart")
@Table(name = "shopping_cart")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems ;

    private BigDecimal priceTotalItems;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    private BigDecimal taxes;

    private BigDecimal shipping;

    private ShoppingCartStatus status;

    private String paymentMethod;

    private BigDecimal priceTotalFinal;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreated() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void recalculateTotals () {
        priceTotalItems = calculatePriceTotalItems();
        priceTotalFinal = calculatePriceTotalFinal();
    }

    public BigDecimal calculatePriceTotalItems() {
        BigDecimal total = cartItems.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        return total;
    }

    public BigDecimal calculatePriceTotalFinal() {
        BigDecimal total = calculatePriceTotalItems();

        if (taxes != null) {
            total = total.subtract(taxes);
        }
        if (shipping != null) {
            total = total.add(shipping);
        }
        if (discount != null) {
            total = total.subtract(discount.getDiscountAmount());
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getPriceTotalFinal() {
        return priceTotalFinal;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public User getUser() {
        return user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getPriceTotalItems() {
        return priceTotalItems;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public BigDecimal getShipping() {
        return shipping;
    }

    public ShoppingCartStatus getStatus() {
        return status;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setPriceTotalItems(BigDecimal priceTotalItems) {
        this.priceTotalItems = priceTotalItems;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public void setShipping(BigDecimal shipping) {
        this.shipping = shipping;
    }

    public void setStatus(ShoppingCartStatus status) {
        this.status = status;
    }

    public void setPriceTotalFinal(BigDecimal priceTotalFinal) {
        this.priceTotalFinal = priceTotalFinal;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
