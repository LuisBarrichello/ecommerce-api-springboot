package com.luisbarrichello.api.ecommerce.repository.paymentMethodRepository;

import com.luisbarrichello.api.ecommerce.model.paymentMethod.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
