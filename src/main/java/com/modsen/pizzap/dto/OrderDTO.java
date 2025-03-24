package com.modsen.pizzap.dto;

import com.modsen.pizzap.models.OrderItem;
import com.modsen.pizzap.models.User;

import java.util.List;

public record OrderDTO(
        Long id,
        Long userId,
        String status,
        List<OrderItemDTO> orderItems
) {
}
