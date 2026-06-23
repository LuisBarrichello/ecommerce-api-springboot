package com.luisbarrichello.api.ecommerce.dto.product;

import com.luisbarrichello.api.ecommerce.model.category.Category;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record ProductUpdateDTO(

        @Size(max = 100, message = "Name must be up to 100 characters")
        String name,

        @Size(max = 500, message = "Description must be up to 500 characters")
        String description,

        @Positive(message = "Price must be positive")
        BigDecimal price,

        @PositiveOrZero(message = "Stock cannot be negative")
        Integer stock,

        Category category,

        @Size(max = 50)
        String SKU,

        @Size(max = 100)
        String brand,

        @URL(message = "Image URL must be a valid URL")
        @Size(max = 100)
        String imgUrl,

        @Positive(message = "Weight must be positive")
        Double weight,

        @Positive(message = "Dimensions must be positive")
        Double dimensions
) {
}
