package com.example.cartservice.controller;

import com.example.cartservice.response.CartResponse;
import com.example.cartservice.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/cart", produces = "application/json")
@Slf4j
public class CartController {
    private CartService cartService;

    @GetMapping("/")
    public Iterable<CartResponse> getAll(){
        return cartService.getAll();
    }
}
