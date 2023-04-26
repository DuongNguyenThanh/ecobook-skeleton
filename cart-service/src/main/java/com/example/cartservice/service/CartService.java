package com.example.cartservice.service;

import com.example.cartservice.model.Cart;
import com.example.cartservice.request.AddCartRequest;
import com.example.cartservice.request.UpdateCartRequest;
import com.example.cartservice.response.CartResponse;

public interface CartService {
    Iterable<CartResponse> getAll();
    Iterable<CartResponse> getByUserId(Integer id);
    Cart addCart(AddCartRequest request);
    Cart updateCart(UpdateCartRequest request);
    void deleteCart(Integer id);
}
