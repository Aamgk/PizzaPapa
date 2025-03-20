package com.modsen.pizzap.services;

import com.modsen.pizzap.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    void createOrder(OrderDTO order);

    void updateOrder(Long orderId, OrderDTO order);

    void deleteOrder(Long orderId);

    OrderDTO getOrder(Long orderId);

    List<OrderDTO> getOrders();
}
