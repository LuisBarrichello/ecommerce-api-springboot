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
    private String imgUrl = "https://example.com/image.jpg";
    private double weight = 1.0;
    private double dimensions = 1.0;

    public ProductBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withStock(int stock) {
        this.stock = stock;
        return this;
    }

    public ProductBuilder withCategory(Category category) {
        this.category = category;
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
        product.setImgUrl(this.imgUrl);
        product.setWeight(this.weight);
        product.setDimensions(this.dimensions);
        return product;
    }
}