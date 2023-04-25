package com.example.orderservice.controller;

import java.util.List;

import com.example.orderservice.payload.request.OrderRequest;
import com.example.orderdatamodel.entity.Order;
import com.example.orderservice.service.OrderService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")  
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order Placed Successffuly";
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return "Order have been deleted";
    }

    @GetMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Integer orderId) {
        orderService.cancelOrder(orderId);
        return "Order have been canceled";
    }

    @PostMapping("/get/{order_id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PostMapping("/getOrderOfCustomer/{customer_id}")
    public ResponseEntity<List<Order>> getOrderByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(orderService.getOrderByCoustomer(customerId));
    }

    @PostMapping("/updateStatus/{orderId}")
    public String updateOrderStatus(@RequestParam("status") String status, @PathVariable Integer orderId) {
        orderService.updateOrderStatus(status, orderId);
        return "Order have been updated";
    }

    @GetMapping("getAllOrder")
    public ResponseEntity<List<Order>> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrder());
    }
}
