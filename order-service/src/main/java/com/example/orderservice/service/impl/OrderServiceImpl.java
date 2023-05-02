package com.example.orderservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.api.exception.NotFoundException;
import com.example.orderdatamodel.entity.enumtype.OrderStatusEnum;
import com.example.orderservice.payload.request.OrderItemRequest;
import com.example.orderservice.payload.request.OrderRequest;
import com.example.orderdatamodel.entity.Order;
import com.example.orderdatamodel.entity.OrderItem;
import com.example.orderservice.payload.response.OrderItemResponse;
import com.example.orderservice.payload.response.OrderResponse;
import com.example.orderservice.repository.OrderItemRepository;
import com.example.orderservice.repository.OrderRepository;

import com.example.orderservice.service.OrderService;
import com.example.proxycommon.ebook.payload.response.BookResponse;
import com.example.proxycommon.ebook.proxy.EbookServiceProxy;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final EbookServiceProxy ebookServiceProxy;

    public void updateOrderStatus(String status, Integer id) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Integer id) {

        return orderRepo.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("getOrderById error: Not found Order with id: %s", id)
                )
        );
    }

    public List<Order> getOrderByCoustomer(Integer customerId) {
        return orderRepository.findByCustomer_id(customerId);
    }

    @Override
    public List<Order> getAllOrder() {

        return orderRepo.findAll();
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

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest, Long userId) {

        Map<Integer, BookResponse> bookMap = new HashMap<>();
        final float[] subTotal = {0f};
        List<OrderItem> items = orderRequest.getOrderItemRequests().stream()
                .map(p -> {
                    BookResponse bookResponse = getBookById(p.getBookId());
                    bookMap.put(p.getBookId(), bookResponse);
                    subTotal[0] += bookResponse.getPrice() * p.getQuantity();

                    return OrderItem.builder()
                            .bookId(p.getBookId())
                            .price(bookResponse.getPrice())
                            .quantity(p.getQuantity())
                            .build();

                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Order order = orderRepo.save(Order.builder()
                        .note(orderRequest.getNote())
                        .status(OrderStatusEnum.ACTIVE)
                        .subTotal(subTotal[0])
                        .userId(userId)
                .build());
        items.forEach(p -> p.setOrder(order));

        List<OrderItemResponse> itemResponses = orderItemRepo.saveAll(items).stream()
                .map(p -> OrderItemResponse.builder()
                        .id(p.getId())
                        .bookId(p.getBookId())
                        .bookName(bookMap.get(p.getBookId()).getName())
                        .totalPrice(p.getPrice() * p.getQuantity())
                        .quantity(p.getQuantity())
                        .build())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .note(order.getNote())
                .itemResponses(itemResponses)
                .build();
    }

    private Order setOrderDetail(OrderRequest orderRequest) {

        Order order = new Order();

        List<OrderItem> orderItems = orderRequest.getListOrderItemDTO()
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

    private OrderItem mapToDto(OrderItemRequest orderItemDto) {
        OrderItem orderItem = new OrderItem();

        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setBookId(orderItemDto.getBookId());
        orderItem.setQuantity(orderItemDto.getQuantity());

        return orderItem;
    }

    private BookResponse getBookById(Integer bookId) {
        BookResponse responses = ebookServiceProxy.getBook(bookId);
        return Objects.isNull(responses) ? null : responses;
    }
} 
