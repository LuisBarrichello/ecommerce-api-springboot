package com.luisbarrichello.api.ecommerce.repository.product;

import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.util.builder.ProductBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should return only products within price range")
    void shouldFilterProductsByPriceRange_whenPriceRangeProvided() {
        Product cheap = new ProductBuilder()
                .withId(null).withName("Camiseta").withPrice(BigDecimal.valueOf(50)).withStock(10).build();
        Product expensive = new ProductBuilder()
                .withId(null).withName("Celular").withPrice(BigDecimal.valueOf(1500)).withStock(5).build();

        productRepository.save(cheap);
        productRepository.save(expensive);

        Specification<Product> spec = ProductSpecification.hasPriceBetween(
                BigDecimal.valueOf(10), BigDecimal.valueOf(100)
        );
        Page<Product> result = productRepository.findAll(spec, PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("Camiseta", result.getContent().getFirst().getName());
    }

    @Test
    @DisplayName("Should return empty page when no products match price range")
    void shouldReturnEmptyPage_whenNoPriceMatch() {
        Product product = new ProductBuilder()
                .withId(null).withName("TV").withPrice(BigDecimal.valueOf(3000)).withStock(2).build();
        productRepository.save(product);

        Specification<Product> spec = ProductSpecification.hasPriceBetween(
                BigDecimal.valueOf(10), BigDecimal.valueOf(100)
        );
        Page<Product> result = productRepository.findAll(spec, PageRequest.of(0, 10));

        assertEquals(0, result.getTotalElements());
    }

    @Test
    @DisplayName("Should return all products when no price filter applied")
    void shouldReturnAllProducts_whenNoPriceFilterApplied() {
        productRepository.save(new ProductBuilder().withId(null).withName("P1").withPrice(BigDecimal.valueOf(10)).withStock(1).build());
        productRepository.save(new ProductBuilder().withId(null).withName("P2").withPrice(BigDecimal.valueOf(500)).withStock(1).build());

        Specification<Product> spec = ProductSpecification.hasPriceBetween(null, null);
        Page<Product> result = productRepository.findAll(spec, PageRequest.of(0, 10));

        assertEquals(2, result.getTotalElements());
    }

    @Test
    @DisplayName("Should filter products by name containing string")
    void shouldFilterByName_whenNameProvided() {
        productRepository.save(new ProductBuilder().withId(null).withName("Notebook Dell").withStock(3).build());
        productRepository.save(new ProductBuilder().withId(null).withName("Notebook Samsung").withStock(3).build());
        productRepository.save(new ProductBuilder().withId(null).withName("Mouse Gamer").withStock(10).build());

        Specification<Product> spec = ProductSpecification.hasName("Notebook");
        Page<Product> result = productRepository.findAll(spec, PageRequest.of(0, 10));

        assertEquals(2, result.getTotalElements());
    }
}