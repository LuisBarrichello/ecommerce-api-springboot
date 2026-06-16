package com.luisbarrichello.api.ecommerce.repository.product;

import com.luisbarrichello.api.ecommerce.model.category.Category;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> hasCategory(Category category) {
        return (root, query, criteriaBuilder) ->
                category == null ? null : criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Product> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            }else if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            } else {
                return null;
            }
        };
    }

    public static Specification<Product> hasBrand(String brand) {
        return (root, query, criteriaBuilder) ->
            brand == null ? null : criteriaBuilder.equal(root.get("brand"), brand);
    }

    public static Specification<Product> isPopular(Boolean isPopular) {
        return (root, query, criteriaBuilder) -> {
            if (isPopular != null && isPopular) {
                query.orderBy(criteriaBuilder.desc(root.get("salesCount")));
            }
            return null;
        };
    }
}
