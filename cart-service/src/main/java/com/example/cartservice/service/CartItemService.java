package com.example.cartservice.service;

import com.example.cartservice.model.CartItem;
import com.example.cartservice.request.AddCartItemRequest;
import com.example.cartservice.request.UpdateCartItemRequest;

public interface CartItemService {
    Iterable<CartItem> getAll();
    Iterable<CartItem> getByCartId(Integer id);
    CartItem addItem(AddCartItemRequest request);
    CartItem updateCartItem(UpdateCartItemRequest request);
    void deleteItem(Integer id);
}
