package com.example.cartservice.service.impl;

import com.example.cartservice.model.CartItem;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.model.Cart;
import com.example.cartservice.payload.request.AddCartRequest;
import com.example.cartservice.payload.request.UpdateCartRequest;
import com.example.cartservice.payload.response.CartResponse;
import com.example.cartservice.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Iterable<CartResponse> getAll() {
        List<Cart> list = cartRepository.findAll();
        List<CartResponse> cartResponses = cartRepository.findAll().stream()
                .map(p -> {
                    return CartResponse.builder()
                            .id(p.getId())
                            .build();
                }).collect(Collectors.toList());
        return null;
    }

    @Override
    public Iterable<CartResponse> getByUserId(Integer id) {
        List<Cart> list = cartRepository.findAll();
        return null;
    }

    @Override
    public Cart addCart(AddCartRequest request) {
        return cartRepository.save(Cart.builder()
                .userId(request.getUserId())
                .items(request.getItems())
                .status(request.getStatus())
                .build());
    }

    @Override
    public Cart updateCart(UpdateCartRequest request) {
        return cartRepository.save(Cart.builder()
                .id(request.getId())
                .userId(request.getUserId())
                .items(request.getItems())
                .status(request.getStatus())
                .build());
    }

    @Override
    public void deleteCart(Integer id) {
        cartRepository.deleteById(id);
    }
}
