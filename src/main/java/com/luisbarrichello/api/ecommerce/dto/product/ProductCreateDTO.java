package com.luisbarrichello.api.ecommerce.dto.product;

import com.luisbarrichello.api.ecommerce.model.category.Category;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record ProductCreateDTO(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Description cannot be blank")
        String description,

        @Positive
        BigDecimal price,

        @Min(value = 0)
        int stock,

        @NotNull
        Category category,

        @NotBlank(message = "SKU cannot be blank")
        String SKU,

        @NotBlank(message = "Brand cannot be blank")
        String brand,

        @NotBlank(message = "IMG cannot be blank")
        @URL
        @Size(max = 100, message = "Url should be up to 100 characters")
        String imgUrl,

        @Positive
        double weight,

        @Positive
        double dimensions
) {
}
