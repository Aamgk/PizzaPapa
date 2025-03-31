package com.modsen.pizzap.controllers;

import com.modsen.pizzap.dto.ProductDTO;
import com.modsen.pizzap.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addProduct(@RequestBody ProductDTO product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@PathVariable Long productId, @RequestBody ProductDTO product) {
        return productService.updateProduct(productId, product);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDTO> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productService.getProducts(pageable);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
