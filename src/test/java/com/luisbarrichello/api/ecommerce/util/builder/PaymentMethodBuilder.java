package com.luisbarrichello.api.ecommerce.util.builder;

import com.luisbarrichello.api.ecommerce.model.paymentMethod.PaymentMethod;

public class PaymentMethodBuilder {
    private Long id = 1L;
    private String method = "PIX";

    public PaymentMethod build() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(this.id);
        paymentMethod.setMethod(this.method);
        return paymentMethod;
    }
}