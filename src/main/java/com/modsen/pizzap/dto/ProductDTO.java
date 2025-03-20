package com.modsen.pizzap.dto;

public record ProductDTO(
        Long id,
        String productName,
        double price,
        String description,
        Long categoryId
) {
}
