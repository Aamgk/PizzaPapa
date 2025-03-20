package com.modsen.pizzap.mappers;

import com.modsen.pizzap.models.Category;
import org.springframework.stereotype.Service;
import com.modsen.pizzap.dto.ProductDTO;
import com.modsen.pizzap.models.Product;
import java.util.function.Function;

@Service
public class ProductMapper implements Function<Product, ProductDTO> {

    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory().getId()
        );
    }

    public Product productDTOToProduct(ProductDTO productDTO) {
        Category category = new Category();
        return new Product(
                productDTO.productName(),
                productDTO.price(),
                productDTO.description(),
                category
        );
    }
}
