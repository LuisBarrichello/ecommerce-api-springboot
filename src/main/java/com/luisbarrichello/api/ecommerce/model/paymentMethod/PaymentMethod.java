package com.luisbarrichello.api.ecommerce.model.paymentMethod;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String method;
}