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
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) throws NullPointerException {
        Order order = new Order();

        List<OrderItem> orderItems = orderRequest.getListOrderItemDto()
            .stream()
            .map(this::mapToDto)
            .toList();
        order.setListOrderItem(orderItems);

        List<Integer> book_ids =  order.getListOrderItem()
            .stream()
            .map(OrderItem::getBook_id)
            .toList();

        // call cart service and place order if is in
        CartRespond[] cartRespondsArray =  webClient.get()
            .uri("http://localhost:", UriBuilder -> UriBuilder.queryParam("book_id", book_ids).build())
            .retrieve()
            .bodyToMono(CartRespond[].class)
            .block();
        /////////////////////////////////////////////
        boolean isAllBooksInStock = Arrays.stream(cartRespondsArray).allMatch(CartRespond::isInStock);
        if(isAllBooksInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Book is not in stook, pls try again later");
        }
        
    }

    private OrderItem mapToDto(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCreated_at(orderItemDto.getCreated_at());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setQuantity(orderItem.getQuantity());
        return orderItem;
    }
} 
