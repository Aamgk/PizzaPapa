package com.modsen.pizzap.services.Impl;

import com.modsen.pizzap.dto.OrderDTO;
import com.modsen.pizzap.dto.OrderItemDTO;
import com.modsen.pizzap.exception.ResourceNotFoundException;
import com.modsen.pizzap.exception.error.ErrorMessages;
import com.modsen.pizzap.mappers.OrderMapper;
import com.modsen.pizzap.models.Order;
import com.modsen.pizzap.models.OrderItem;
import com.modsen.pizzap.models.OrderStatus;
import com.modsen.pizzap.repositories.OrderItemRepository;
import com.modsen.pizzap.repositories.OrderRepository;
import com.modsen.pizzap.repositories.ProductRepository;
import com.modsen.pizzap.repositories.UserRepository;
import com.modsen.pizzap.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public OrderDTO createOrder(OrderDTO order) {
        Order newOrder = orderMapper.orderDTOtoEntity(order);
        newOrder.setStatus(OrderStatus.PENDING);
        orderRepository.save(newOrder);

        for(OrderItemDTO orderItem : order.orderItems()) {
            OrderItem orderItem1 = new OrderItem();
            orderItem1.setOrder(newOrder);
            orderItem1.setProduct(productRepository.findById(orderItem.productId()).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_MESSAGE, "Product", orderItem.productId()))));
            orderItem1.setQuantity(orderItem.quantity());
            orderItemRepository.save(orderItem1);
        }
        return orderMapper.apply(newOrder);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO order) {
        Order updatedOrder = orderMapper.orderDTOtoEntity(order);
        updatedOrder.setUser(userRepository.findById(order.userId()).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_MESSAGE, "User", order.userId()))));
        Order existingOrder = findOrderById(orderId);
        existingOrder.setOrderItems(updatedOrder.getOrderItems());
        existingOrder.setUser(updatedOrder.getUser());
        existingOrder.setStatus(updatedOrder.getStatus());
        return orderMapper.apply(orderRepository.save(existingOrder));
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        Order order = findOrderById(orderId);
        return orderMapper.apply(order);
    }

    @Override
    public Page<OrderDTO> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_MESSAGE, "Order", orderId)));
    }
}
