package com.example.cartservice.response;

import com.example.cartservice.model.Cart;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Integer id;
    @JsonProperty("book_id")
    private Integer bookId;
    private Float price;
    private Integer quantity;
    private CartResponse cart;
}
