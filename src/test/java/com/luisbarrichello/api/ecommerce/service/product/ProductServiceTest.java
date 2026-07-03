package com.luisbarrichello.api.ecommerce.service.product;

import com.luisbarrichello.api.ecommerce.dto.product.ProductCreateDTO;
import com.luisbarrichello.api.ecommerce.dto.product.ProductResponseDTO;
import com.luisbarrichello.api.ecommerce.dto.product.ProductSummaryListDTO;
import com.luisbarrichello.api.ecommerce.dto.product.ProductUpdateDTO;
import com.luisbarrichello.api.ecommerce.model.category.Category;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.repository.product.ProductRepository;
import com.luisbarrichello.api.ecommerce.util.builder.ProductBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should create product and save to repository")
    void shouldCreateProduct_whenDataIsValid() {
        ProductCreateDTO dto = new ProductCreateDTO(
                "Celular", "Smartphone top", BigDecimal.valueOf(1500), 10,
                Category.ELETRONICOS, "SKU-001", "Samsung",
                "https://img.com/cel.jpg", 0.2, 15.0
        );
        Product saved = new ProductBuilder()
                .withName("Celular")
                .withPrice(BigDecimal.valueOf(1500))
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        Product result = productService.createProduct(dto);

        assertNotNull(result);
        assertEquals("Celular", result.getName());
        assertEquals(BigDecimal.valueOf(1500), result.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should update only non-null fields")
    void shouldUpdateProduct_whenOnlyNameProvided() {
        Product existing = new ProductBuilder().withName("Nome Antigo").build();

        ProductUpdateDTO updateDTO = new ProductUpdateDTO(
                "Nome Novo", null, null, null, null, null, null, null, null, null
        );

        when(productRepository.getReferenceById(1L)).thenReturn(existing);

        ProductResponseDTO result = productService.updateProduct(1L, updateDTO);

        assertNotNull(result);
        assertEquals("Nome Novo", result.name());
        assertEquals(BigDecimal.valueOf(150.0), result.price());
        verify(productRepository, times(1)).save(existing);
    }

    @Test
    @DisplayName("Should update price when provided")
    void shouldUpdatePrice_whenPriceIsProvided() {
        Product existing = new ProductBuilder().build();

        ProductUpdateDTO updateDTO = new ProductUpdateDTO(
                null, null, BigDecimal.valueOf(999), null,
                null, null, null, null, null, null
        );

        when(productRepository.getReferenceById(1L)).thenReturn(existing);

        ProductResponseDTO result = productService.updateProduct(1L, updateDTO);

        assertEquals(BigDecimal.valueOf(999), result.price());
    }

    @Test
    @DisplayName("Should delete product when it exists")
    void shouldDeleteProduct_whenProductExists() {
        Product product = new ProductBuilder().build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when product does not exist")
    void shouldThrowEntityNotFoundException_whenProductNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> productService.deleteProduct(99L));

        verify(productRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should return paginated list of products")
    void shouldReturnPageOfProducts_whenProductsExist() {
        Pageable pageable = PageRequest.of(0, 10);
        Product product = new ProductBuilder().withName("Notebook").build();
        Page<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<ProductSummaryListDTO> result = productService.listAllProducts(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Notebook", result.getContent().getFirst().name());
    }

    @Test
    @DisplayName("Should return empty page when no products exist")
    void shouldReturnEmptyPage_whenNoProductsExist() {
        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<ProductSummaryListDTO> result = productService.listAllProducts(pageable);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }
}