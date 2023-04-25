package com.example.orderservice.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.orderservice.dto.OrderItemDto;
import com.example.orderservice.payload.request.OrderRequest;
import com.example.orderdatamodel.entity.Order;
import com.example.orderdatamodel.entity.OrderItem;
import com.example.orderservice.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void updateOrderStatus(String status, Integer id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(status);
        orderRepository.save(order);
    }

    public Order getOrder(Integer id) {
        return orderRepository.findById(id).get();
    }

    public List<Order> getOrderByCoustomer(Integer customerId) {
        return orderRepository.findByCustomer_id(customerId);
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public void cancelOrder(Integer id) {
        // call cartService to re-add listOrderItem to cart
        // delete order
        orderRepository.deleteById(id);
    }

    public void deleteOrder(Integer id) {
        if(orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    public void placeOrder(OrderRequest orderRequest) throws NullPointerException {

        Order order = setOrderDetail(orderRequest);

        // List<Integer> book_ids =  order.getListOrderItem()
        //     .stream()
        //     .map(OrderItem::getBook_id)
        //     .toList();

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
            .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        // set user_id for order
        // set note
        order.setNote(orderRequest.getNote());
        // set status
        order.setStatus("Watting for payment");
        // set sub_total
        float sub_total = 0;
        for (OrderItem orderItem : orderItems) {
           sub_total += orderItem.getPrice() * orderItem.getQuantity();
        }
        order.setSubTotal(sub_total);

        return order;
    }

    private OrderItem mapToDto(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();

        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setBookId(orderItemDto.getBookId());
        orderItem.setQuantity(orderItemDto.getQuantity());

        return orderItem;
    }
} 
