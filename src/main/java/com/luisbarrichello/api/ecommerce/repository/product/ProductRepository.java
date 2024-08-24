package com.luisbarrichello.api.ecommerce.repository.product;

import com.luisbarrichello.api.ecommerce.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
