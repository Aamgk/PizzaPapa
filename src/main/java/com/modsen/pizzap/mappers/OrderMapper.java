package com.modsen.pizzap.mappers;

import com.modsen.pizzap.dto.OrderItemDTO;
import com.modsen.pizzap.models.OrderItem;
import com.modsen.pizzap.models.User;
import com.modsen.pizzap.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.modsen.pizzap.dto.OrderDTO;
import com.modsen.pizzap.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class OrderMapper implements Function<Order, OrderDTO> {
    private final UserRepository userRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDTO apply(Order order) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemDTO orderItemDTO = orderItemMapper.apply(orderItem);
            orderItems.add(orderItemDTO);
        }
        return new OrderDTO(
                order.getId(),
                order.getUser().getId(),
                order.getStatus().toString(),
                orderItems
        );
    }

    public Order orderDTOtoEntity(OrderDTO orderDTO) {
        User user = new User();
        List<OrderItem> orderItem = new ArrayList<>();
        return new Order(
                user,
                orderDTO.status(),
                orderItem
        );
    }
}
