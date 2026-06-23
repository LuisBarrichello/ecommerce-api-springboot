package com.luisbarrichello.api.ecommerce.controller;

import com.luisbarrichello.api.ecommerce.dto.product.*;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.repository.product.ProductRepository;
import com.luisbarrichello.api.ecommerce.service.product.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity<ProductResponseDTO> registerProduct(
            @RequestBody @Valid ProductCreateDTO productCreateDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Product product = productService.createProduct(productCreateDTO);
        URI uri = uriComponentsBuilder.path("products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductResponseDTO(product));
    }

    @GetMapping
    public ResponseEntity<Page<ProductSummaryListDTO>> listAllProducts(@PageableDefault Pageable pageable) {
        Page<ProductSummaryListDTO> products = productService.listAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        ProductResponseDTO product = productRepository.findById(id)
                .map(ProductResponseDTO::new)
                .orElseThrow(() -> new ValidationException("Produto não encontrado"));
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdateDTO productUpdateDTO
    ) {
        ProductResponseDTO product = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProducts(
            @PageableDefault Pageable pageable,
            ProductSearchDTO productSearchDTO
    ) {
        Page<ProductResponseDTO> products = productService.searchProducts(productSearchDTO, pageable);
        return ResponseEntity.ok(products);
    }
}
