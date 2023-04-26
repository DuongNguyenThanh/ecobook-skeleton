package com.example.cartservice.service.impl;

import com.example.cartservice.data.CartItemRepository;
import com.example.cartservice.model.CartItem;
import com.example.cartservice.request.AddCartItemRequest;
import com.example.cartservice.request.UpdateCartItemRequest;
import com.example.cartservice.service.CartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Iterable<CartItem> getAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public Iterable<CartItem> getByCartId(Integer id) {
        return cartItemRepository.findAllByCartId(id);
    }

    @Override
    public CartItem addItem(AddCartItemRequest request) {
        return cartItemRepository.save(CartItem.builder()
                .cart(request.getCart())
                .price(request.getPrice())
                .bookId(request.getBookId())
                .quantity(request.getQuantity()).build());
    }

    @Override
    public CartItem updateCartItem(UpdateCartItemRequest request) {
        return cartItemRepository.save(CartItem.builder()
                .id(request.getId())
                .cart(request.getCart())
                .price(request.getPrice())
                .bookId(request.getBookId())
                .quantity(request.getQuantity()).build());
    }

    @Override
    public void deleteItem(Integer id) {
        cartItemRepository.deleteById(id);
    }
}
