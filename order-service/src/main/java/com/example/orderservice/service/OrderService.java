package com.example.orderservice.service;

import com.example.orderdatamodel.entity.Order;
import com.example.orderservice.payload.request.OrderRequest;
import com.example.orderservice.payload.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest orderRequest, Long userId);

    List<Order> getAllOrder();

    Order getOrderById(Integer id);
}
