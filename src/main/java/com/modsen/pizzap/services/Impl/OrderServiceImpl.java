package com.modsen.pizzap.services.Impl;

import com.modsen.pizzap.dto.OrderDTO;
import com.modsen.pizzap.dto.OrderItemDTO;
import com.modsen.pizzap.mappers.OrderMapper;
import com.modsen.pizzap.models.Order;
import com.modsen.pizzap.models.OrderItem;
import com.modsen.pizzap.repositories.OrderItemRepository;
import com.modsen.pizzap.repositories.OrderRepository;
import com.modsen.pizzap.repositories.ProductRepository;
import com.modsen.pizzap.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;

    @Override
    public void createOrder(OrderDTO order) {
        Order newOrder = orderMapper.orderDTOtoEntity(order);
        orderRepository.save(newOrder);

        for(OrderItemDTO orderItem : order.orderItems()) {
            OrderItem orderItem1 = new OrderItem();
            orderItem1.setOrder(newOrder);
            orderItem1.setProduct(productRepository.findById(orderItem.productId()).orElseThrow(() -> new RuntimeException("Product not found")));
            orderItem1.setQuantity(orderItem.quantity());
            orderItemRepository.save(orderItem1);
        }
    }

    @Override
    public void updateOrder(Long orderId, OrderDTO order) {
        Order updatedOrder = orderMapper.orderDTOtoEntity(order);
        Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        existingOrder.setOrderItems(updatedOrder.getOrderItems());
        existingOrder.setUser(updatedOrder.getUser());
        existingOrder.setStatus(updatedOrder.getStatus());
        orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<OrderDTO> getOrders() {
        return new ArrayList<>(orderRepository.findAll())
                .stream()
                .map(orderMapper).toList();
    }
}
