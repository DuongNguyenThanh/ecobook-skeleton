package com.example.cartservice.controller;

import com.example.api.controller.BaseController;
import com.example.cartservice.payload.request.CartItemRequest;
import com.example.cartservice.payload.response.CartResponse;
import com.example.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "api/cart")
public class CartController extends BaseController {

    private final CartService cartService;

    @GetMapping("/all-cart")
    public List<CartResponse> getAll(){
        return null;
    }

    @GetMapping("/active-cart")
    public List<CartResponse> getActiveCart(){
        return cartService.getActiveCart(1L);
    }

//    @PostMapping("/update-cart")
//    public ResponseEntity<?> updateCart(
//            @RequestBody CartItemRequest request
//    ) {
//        Long userId = getOriginalId();
//
//    }



}
