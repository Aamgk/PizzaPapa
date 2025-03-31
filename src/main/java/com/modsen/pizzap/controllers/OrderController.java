package com.modsen.pizzap.controllers;

import com.modsen.pizzap.dto.OrderDTO;
import com.modsen.pizzap.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO order) {
        return orderService.updateOrder(orderId, order);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDTO> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return orderService.getOrders(pageable);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
