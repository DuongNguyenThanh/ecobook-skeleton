package com.example.orderservice.controller;

import com.example.api.controller.BaseController;
import com.example.orderdatamodel.entity.Order;
import com.example.orderservice.payload.request.OrderRequest;
import com.example.orderservice.payload.response.OrderResponse;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController extends BaseController {

    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody OrderRequest orderRequest
    ) {

        Long userId = getOriginalId();
        return ResponseEntity.ok(orderService.placeOrder(orderRequest, userId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Order>> getAllOrder() {

        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(
            @PathVariable Integer orderId
    ) {

        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping("/getOrderOfCustomer/{customer_id}")
    public ResponseEntity<List<Order>> getOrderByCustomer(
            @PathVariable Integer customerId
    ) {

        return ResponseEntity.ok(orderServiceImpl.getOrderByCoustomer(customerId));
    }


    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(
            @PathVariable Integer orderId
    ) {

        orderServiceImpl.deleteOrder(orderId);
        return "Order have been deleted";
    }

    @GetMapping("/cancel/{orderId}")
    public String cancelOrder(
            @PathVariable Integer orderId
    ) {

        orderServiceImpl.cancelOrder(orderId);
        return "Order have been canceled";
    }

    @PostMapping("/updateStatus/{orderId}")
    public String updateOrderStatus(
            @RequestParam("status") String status,
            @PathVariable Integer orderId
    ) {

        orderServiceImpl.updateOrderStatus(status, orderId);
        return "Order have been updated";
    }
}
