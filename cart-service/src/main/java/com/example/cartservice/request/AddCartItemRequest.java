package com.example.cartservice.request;

import com.example.cartservice.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequest {
    private Integer bookId;
    private Float price;
    private Integer quantity;
    private Cart cart;
}
