package com.example.cartservice.response;

import com.example.cartservice.model.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private Integer id;
    @JsonProperty("user_id")
    private Integer userId;
    private String status;
    private List<CartItemResponse> items;
}
