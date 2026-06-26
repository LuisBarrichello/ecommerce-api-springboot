package com.luisbarrichello.api.ecommerce.util.builder;

import com.luisbarrichello.api.ecommerce.model.orderItem.OrderItem;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import java.math.BigDecimal;

public class OrderItemBuilder {
    private Long id = 1L;
    private Product product = new ProductBuilder().build();
    private String productName = "Produto Genérico";
    private BigDecimal priceAtPurchase = BigDecimal.valueOf(150.0);
    private Integer quantity = 1;

    public OrderItemBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderItemBuilder withProduct(Product product) {
        this.product = product;
        this.productName = product.getName();
        this.priceAtPurchase = product.getPrice();
        return this;
    }

    public OrderItem build() {
        OrderItem item = new OrderItem();
        item.setId(this.id);
        item.setProduct(this.product);
        item.setProductName(this.productName);
        item.setPriceAtPurchase(this.priceAtPurchase);
        item.setQuantity(this.quantity);
        return item;
    }
}