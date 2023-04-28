package com.example.cartservice.controller;

import com.example.cartservice.payload.response.CartResponse;
import com.example.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/all-cart")
    public List<CartResponse> getAll(){
        return null;
    }

    @GetMapping("/active-cart")
    public List<CartResponse> getActiveCart(){
        return cartService.getActiveCart(1L);
    }


}
