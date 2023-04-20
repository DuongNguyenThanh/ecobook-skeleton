package com.example.order_service.order_service.controller;

import com.example.order_service.order_service.dto.OrderRequest;
import com.example.order_service.order_service.service.OrderService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")  
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest OrderRequest) {
        orderService.placeOrder(OrderRequest);
        return "Order Placed Successffuly";
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return "Order have been deleted";
    }

    @GetMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Integer orderId) {
        return "Order have been canceled";
    }

}
