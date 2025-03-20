package com.modsen.pizzap.services;

import com.modsen.pizzap.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    void createProduct(ProductDTO product);

    void updateProduct(Long productId, ProductDTO product);

    void deleteProduct(Long productId);

    ProductDTO getProduct(Long productId);

    List<ProductDTO> getProducts();
}
