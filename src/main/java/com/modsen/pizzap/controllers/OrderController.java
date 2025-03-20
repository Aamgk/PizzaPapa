package com.modsen.pizzap.controllers;

import com.modsen.pizzap.dto.OrderDTO;
import com.modsen.pizzap.services.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Data
@RestController
@RequestMapping("v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public void createOrder(@RequestBody OrderDTO order) {
        orderService.createOrder(order);
    }

    @PostMapping("/updateOrder/{orderId}")
    public void updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO order) {
        orderService.updateOrder(orderId, order);
    }

    @GetMapping("/getOrder/{orderId}")
    public OrderDTO getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/getAllOrders")
    public List<OrderDTO> getOrders() {
        return orderService.getOrders();
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
