package com.luisbarrichello.api.ecommerce.service.product;

import com.luisbarrichello.api.ecommerce.dto.product.*;
import com.luisbarrichello.api.ecommerce.model.product.Product;
import com.luisbarrichello.api.ecommerce.repository.product.ProductRepository;
import com.luisbarrichello.api.ecommerce.repository.product.ProductSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductUpdateDTO productUpdateDTO) {
        Product product = productRepository.getReferenceById(id);

        if(productUpdateDTO.name() != null) product.setName(productUpdateDTO.name());
        if (productUpdateDTO.description() != null) product.setDescription(productUpdateDTO.description());
        if (productUpdateDTO.price() != null) product.setPrice(productUpdateDTO.price());
        if (productUpdateDTO.stock() != null) product.setStock(productUpdateDTO.stock());
        if (productUpdateDTO.category() != null) product.setCategory(productUpdateDTO.category());
        if (productUpdateDTO.SKU() != null) product.setSKU(productUpdateDTO.SKU());
        if (productUpdateDTO.brand() != null) product.setBrand(productUpdateDTO.brand());
        if (productUpdateDTO.imgUrl() != null) product.setImgUrl(productUpdateDTO.imgUrl());
        if (productUpdateDTO.weight() != null) product.setWeight(productUpdateDTO.weight());
        if (productUpdateDTO.dimensions() != null) product.setDimensions(productUpdateDTO.dimensions());

        productRepository.save(product);
        return new ProductResponseDTO(product);
    }

    @Transactional
    public Product createProduct(ProductCreateDTO productCreateDTO) {
        Product product = new Product(productCreateDTO);
        productRepository.save(product);
        return product;
    }

    public Page<ProductSummaryListDTO> listAllProducts(Pageable pageable) {
            Page<ProductSummaryListDTO> products = productRepository.findAll(pageable).map(ProductSummaryListDTO::new);
        return products;
    }

    @Transactional
    public void deleteProduct(Long id) {
        var product = productRepository.findById(id);
        if(product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Produto não encontrado.");
        }
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> searchProducts(ProductSearchDTO productSearchDTO, Pageable pageable) {
        Specification<Product> specification = Specification.
                where(ProductSpecification.hasName(productSearchDTO.name()))
                .and(ProductSpecification.hasCategory(productSearchDTO.category()))
                .and(ProductSpecification.hasBrand(productSearchDTO.brand()))
                .and(ProductSpecification.hasPriceBetween(productSearchDTO.minPrice(), productSearchDTO.maxPrice()))
                .and(ProductSpecification.isPopular(productSearchDTO.popular()));

        if (productSearchDTO.sortBy() != null) {
            Sort sort =
                    productSearchDTO.ascending() ?
                            Sort.by(productSearchDTO.sortBy()).ascending() :
                            Sort.by(productSearchDTO.sortBy()).descending();

            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        return productRepository.findAll(specification, pageable).map(ProductResponseDTO::new);
    }
}
