package com.example.cartservice.service;

import com.example.cartservice.payload.request.DelCartItemRequest;

public interface CartItemService {

    void deleteCartItem(DelCartItemRequest request);
}
