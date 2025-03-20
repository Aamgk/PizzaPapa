package com.modsen.pizzap.controllers;

import com.modsen.pizzap.dto.ProductDTO;
import com.modsen.pizzap.services.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Data
@RestController
@RequestMapping("v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody ProductDTO product) {
        productService.createProduct(product);
    }

    @PostMapping("/updateProduct/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody ProductDTO product) {
        productService.updateProduct(productId, product);
    }

    @GetMapping("/getProduct/{productId}")
    public ProductDTO getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/getAllProducts")
    public List<ProductDTO> getAllProducts() {
        return productService.getProducts();
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
