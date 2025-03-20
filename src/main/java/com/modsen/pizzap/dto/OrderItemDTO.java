package com.modsen.pizzap.dto;


public record OrderItemDTO(
        Long id,
        Long orderId,
        Long productId,
        Integer quantity
) {
}
