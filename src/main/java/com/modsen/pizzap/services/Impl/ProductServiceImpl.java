package com.modsen.pizzap.services.Impl;

import com.modsen.pizzap.dto.ProductDTO;
import com.modsen.pizzap.exception.DuplicateResourceException;
import com.modsen.pizzap.exception.ResourceNotFoundException;
import com.modsen.pizzap.exception.error.ErrorMessages;
import com.modsen.pizzap.mappers.ProductMapper;
import com.modsen.pizzap.models.Product;
import com.modsen.pizzap.repositories.CategoryRepository;
import com.modsen.pizzap.repositories.ProductRepository;
import com.modsen.pizzap.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO) {
        checkProductExistence(productDTO.productName());
        Product product = productMapper.productDTOToProduct(productDTO);
        product.setCategory(categoryRepository.findById(productDTO.categoryId()).orElseThrow(() -> new RuntimeException("Category not found")));
        productRepository.save(product);
        return new ResponseEntity<>(
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<ProductDTO> updateProduct(Long productId, ProductDTO product) {
        Product updatedProduct = productMapper.productDTOToProduct(product);
        updatedProduct.setCategory(categoryRepository.findById(product.categoryId()).orElseThrow(() -> new RuntimeException("Category not found")));
        Product oldProduct = findProductByIdOrThrow(productId);
        oldProduct.setProductName(updatedProduct.getProductName());
        oldProduct.setDescription(updatedProduct.getDescription());
        oldProduct.setPrice(updatedProduct.getPrice());
        oldProduct.setCategory(updatedProduct.getCategory());
        productRepository.save(oldProduct);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = findProductByIdOrThrow(productId);
        productRepository.delete(product);
    }

    @Override
    public ProductDTO getProduct(Long productId) {
        Product product = findProductByIdOrThrow(productId);
        return productMapper.apply(product);
    }

    @Override
    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper);
    }

    private Product findProductByIdOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_MESSAGE, "Product", productId)));
    }

    private void checkProductExistence(String productName){
        if(productRepository.existsByProductName(productName)){
            throw new DuplicateResourceException(String.format(ErrorMessages.DUPLICATE_RESOURCE_MESSAGE, "Product", "id"));
        }
    }
}
