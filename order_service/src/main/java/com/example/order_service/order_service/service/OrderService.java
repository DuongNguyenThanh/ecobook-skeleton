package com.example.order_service.order_service.service;

import java.util.Arrays;
import java.util.List;

import com.example.order_service.order_service.dto.CartRespond;
import com.example.order_service.order_service.dto.OrderItemDto;
import com.example.order_service.order_service.dto.OrderRequest;
import com.example.order_service.order_service.model.Order;
import com.example.order_service.order_service.model.OrderItem;
import com.example.order_service.order_service.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) throws NullPointerException {
        Order order = setOrderDetail(orderRequest);

        List<Integer> book_ids =  order.getListOrderItem()
            .stream()
            .map(OrderItem::getBook_id)
            .toList();

        // call cart service and place order if is in
        // CartRespond[] cartRespondsArray =  webClientBuilder.build().get()
        //     .uri("http://localhost:", UriBuilder -> UriBuilder.queryParam("book_id", book_ids).build())
        //     .retrieve()
        //     .bodyToMono(CartRespond[].class)
        //     .block();
        // /////////////////////////////////////////////
        // boolean isAllBooksInStock = Arrays.stream(cartRespondsArray).allMatch(CartRespond::isInStock);
        // if(isAllBooksInStock) {
        //     orderRepository.save(order);
        // } else {
        //     throw new IllegalArgumentException("Book is not in stook, pls try again later");
        // }
        
        orderRepository.save(order);
    }

    private Order setOrderDetail(OrderRequest orderRequest) {
        Order order = new Order();

        List<OrderItem> orderItems = orderRequest.getListOrderItemDto()
            .stream()
            .map(this::mapToDto)
            .toList();
        order.setListOrderItem(orderItems);
        // set user_id for order
        // set note
        // set status
        order.setStatus("Watting for payment");
        // set sub_total
        float sub_total = 0;
        for (OrderItem orderItem : orderItems) {
           sub_total += orderItem.getPrice() * orderItem.getQuantity();
        }
        order.setSub_total(sub_total);

        return order;
    }

    private OrderItem mapToDto(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();

        orderItem.setCreated_at(orderItemDto.getCreated_at());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setBook_id(orderItemDto.getBook_id());
        orderItem.setQuantity(orderItemDto.getQuantity());

        return orderItem;
    }
} 
