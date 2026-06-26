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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void deleteProduct_Success() {
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);

        Mockito.when(productRepository.findById(mockProduct.getId())).thenReturn(Optional.of(mockProduct));

        productService.deleteProduct(mockProduct.getId());

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(mockProduct.getId());
    }

    @Test
    void deleteProduct_ThrowsEntityNotFoundException() {
        Long productId = 99L;

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            productService.deleteProduct(productId);
        });

        Mockito.verify(productRepository, Mockito.never()).deleteById(productId);
    }

    @Test
    void mustCreateAProduct() {
        ProductCreateDTO dto = new ProductCreateDTO("Testing", "We are testing", BigDecimal.TEN, 10, Category.AUTOMOTIVO, "131165", "Brand", "url", 1.0, 1.0);
        Product product = new Product(dto);
        product.setId(1L);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Testing", result.getName());

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }

    @Test
    void mustUpdateAProduct() {
        ProductCreateDTO dto = new ProductCreateDTO("Testing", "We are testing", BigDecimal.TEN, 10, Category.AUTOMOTIVO, "131165", "Brand", "url", 1.0, 1.0);
        Product oldProduct = new Product(dto);
        Long productID = 1L;

        ProductUpdateDTO updateDTO = new ProductUpdateDTO(
                "Nome Novo",
                null, null, null, null, null, null, null, null, null
        );

        Mockito.when(productRepository.getReferenceById(productID)).thenReturn(oldProduct);

        ProductResponseDTO result = productService.updateProduct(productID, updateDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Novo nome", result.name());

        Mockito.verify(productRepository, Mockito.times(1)).save(oldProduct);
    }

    @Test
    void listAllProductsTest() {
        Pageable pageable = PageRequest.of(0, 10);

        Product product = new ProductBuilder().build();
        Page<Product> pageProducts = new PageImpl<>(List.of(product));

        Mockito.when(
                productRepository
                        .findAll(pageable))
                .thenReturn(pageProducts);

        Page<ProductSummaryListDTO> result = productService.listAllProducts(pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(product.getName(), result.getContent().getFirst().name());
    }
}
