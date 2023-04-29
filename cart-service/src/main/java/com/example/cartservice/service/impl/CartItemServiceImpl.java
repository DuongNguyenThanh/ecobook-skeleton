package com.example.cartservice.service.impl;

import com.example.api.exception.NotFoundException;
import com.example.cartdatamodel.entity.Cart;
import com.example.cartservice.payload.request.DelCartItemRequest;
import com.example.cartservice.repository.CartItemRepository;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;

    @Override
    public void deleteCartItem(DelCartItemRequest request) {

        Cart cart = cartRepo.findById(request.getCartId()).orElseThrow(
                () -> new NotFoundException(
                      String.format("deleteCartItem error: Not found Cart with id: %s", request.getCartId())
                )
        );

        if(cart.getItems().isEmpty() ||
                (cart.getItems().size() == 1 &&
                        cart.getItems().get(0).getId().equals(request.getCartItemId())
                )
        ) {
            cartRepo.delete(cart);
        }

        cartItemRepo.deleteById(request.getCartItemId());
    }
}
