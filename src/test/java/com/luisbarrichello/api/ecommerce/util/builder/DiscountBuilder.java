package com.luisbarrichello.api.ecommerce.util.builder;

import com.luisbarrichello.api.ecommerce.model.shoppingCart.Discount;
import java.math.BigDecimal;

public class DiscountBuilder {
    private Long id = 1L;
    private String discountCode = "PROMO10";
    private BigDecimal discountAmount = BigDecimal.valueOf(10.0);

    public Discount build() {
        Discount discount = new Discount();
        discount.setId(this.id);
        discount.setDiscountCode(this.discountCode);
        discount.setDiscountAmount(this.discountAmount);
        return discount;
    }
}