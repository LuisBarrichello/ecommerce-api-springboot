package com.luisbarrichello.api.ecommerce.util.builder;

import com.luisbarrichello.api.ecommerce.model.category.Category;
import com.luisbarrichello.api.ecommerce.model.product.Product;

import java.math.BigDecimal;

public class ProductBuilder {
    private Long id = 1L;
    private String name = "Produto Genérico";
    private String description = "Descrição incrível";
    private BigDecimal price = BigDecimal.valueOf(150.0);
    private int stock = 10;
    private Category category = Category.ELETRONICOS;
    private String sku = "SKU-12345";
    private String brand = "Samsung";

    public ProductBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withStock(int stock) {
        this.stock = stock;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setStock(this.stock);
        product.setCategory(this.category);
        product.setSKU(this.sku);
        product.setBrand(this.brand);
        return product;
    }
}