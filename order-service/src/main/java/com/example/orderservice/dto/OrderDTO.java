package com.example.orderservice.dto;

import com.example.orderservice.payload.request.OrderItemRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderDTO {

    private Integer id;

    private Float subTotal;

    private String status;

    private String note;

    private Long userId;

    private List<OrderItemRequest> orderItems;
}
