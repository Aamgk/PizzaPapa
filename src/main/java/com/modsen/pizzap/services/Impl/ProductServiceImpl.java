package com.modsen.pizzap.services.Impl;

import com.modsen.pizzap.dto.ProductDTO;
import com.modsen.pizzap.mappers.ProductMapper;
import com.modsen.pizzap.models.Product;
import com.modsen.pizzap.repositories.CategoryRepository;
import com.modsen.pizzap.repositories.ProductRepository;
import com.modsen.pizzap.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public void createProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        product.setCategory(categoryRepository.findById(productDTO.categoryId()).orElseThrow(() -> new RuntimeException("Category not found")));
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long productId, ProductDTO product) {
        Product updatedProduct = productMapper.productDTOToProduct(product);
        updatedProduct.setCategory(categoryRepository.findById(product.categoryId()).orElseThrow(() -> new RuntimeException("Category not found")));
        Product oldProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        oldProduct.setProductName(updatedProduct.getProductName());
        oldProduct.setDescription(updatedProduct.getDescription());
        oldProduct.setPrice(updatedProduct.getPrice());
        oldProduct.setCategory(updatedProduct.getCategory());
        productRepository.save(oldProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductDTO getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductDTO> getProducts() {
        return new ArrayList<>(productRepository.findAll())
                .stream()
                .map(productMapper).toList();
    }
}
