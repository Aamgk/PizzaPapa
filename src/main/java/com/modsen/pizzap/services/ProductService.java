package com.modsen.pizzap.services;

import com.modsen.pizzap.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ProductDTO createProduct(ProductDTO product);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    void deleteProduct(Long productId);

    ProductDTO getProduct(Long productId);

    Page<ProductDTO> getProducts(Pageable pageable);
}
