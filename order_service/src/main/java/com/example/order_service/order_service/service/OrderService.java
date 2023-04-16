package com.example.order_service.order_service.service;

import java.util.ArrayList;
import java.util.List;

import com.example.order_service.order_service.dto.OrderItemDto;
import com.example.order_service.order_service.dto.OrderRequest;
import com.example.order_service.order_service.model.Order;
import com.example.order_service.order_service.model.OrderItem;
import com.example.order_service.order_service.repository.OrderRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setNote("test");
        order.setStatus("test");
        order.setSub_total(123);
        // List<OrderItem> orderItems = orderRequest.getOrderItemDto()
        //     .stream()
        //     .map(this::mapToDto)
        //     .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        // order.setListOrderItem(orderItems);

        orderRepository.save(order);
    }

    private OrderItem mapToDto(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCreated_at(orderItemDto.getCreated_at().toString());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setQuantity(orderItem.getQuantity());
        return orderItem;
    }
} 
