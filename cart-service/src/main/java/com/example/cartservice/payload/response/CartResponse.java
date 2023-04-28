package com.example.cartservice.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CartResponse {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("items")
    private List<CartItemResponse> items;
}
