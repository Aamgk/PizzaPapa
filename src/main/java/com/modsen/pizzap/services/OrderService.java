package com.modsen.pizzap.services;

import com.modsen.pizzap.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<OrderDTO> createOrder(OrderDTO order);

    ResponseEntity<OrderDTO> updateOrder(Long orderId, OrderDTO order);

    void deleteOrder(Long orderId);

    OrderDTO getOrder(Long orderId);

    Page<OrderDTO> getOrders(Pageable pageable);
}
