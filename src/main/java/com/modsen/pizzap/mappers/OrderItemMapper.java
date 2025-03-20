package com.modsen.pizzap.mappers;

import com.modsen.pizzap.dto.OrderItemDTO;
import com.modsen.pizzap.models.Order;
import com.modsen.pizzap.models.OrderItem;
import com.modsen.pizzap.models.Product;
import com.modsen.pizzap.repositories.OrderRepository;
import com.modsen.pizzap.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class OrderItemMapper implements Function<OrderItem, OrderItemDTO> {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderItemDTO apply(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity()
        );
    }

    public OrderItem orderItemDtoToOrderItem(OrderItemDTO dto) {
        Optional<Order> order = orderRepository.findById(dto.orderId());
        Optional<Product> product = productRepository.findById(dto.productId());
        return new OrderItem(
                order,
                product,
                dto.quantity()
        );
    }
}
