package com.example.cartservice.service.impl;

import com.example.cartdatamodel.entity.Cart;
import com.example.cartdatamodel.entity.CartItem;
import com.example.cartdatamodel.entity.enumtype.StatusEnum;
import com.example.cartservice.payload.request.CartItemRequest;
import com.example.cartservice.payload.response.CartItemResponse;
import com.example.cartservice.repository.CartItemRepository;
import com.example.cartservice.repository.CartRepository;
import com.example.cartservice.payload.response.CartResponse;
import com.example.cartservice.service.CartService;
import com.example.proxycommon.ebook.payload.response.BookResponse;
import com.example.proxycommon.ebook.proxy.EbookServiceProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final EbookServiceProxy proxy;

    @Override
    public List<CartResponse> getActiveCart(Long userId) {

//        Cart cart = cartRepo.findByStatusAndUserId(StatusEnum.ACTIVE.name(), userId);
//        List<Integer> bookIds = cart.getItems().stream()
//                .map(CartItem::getBookId).collect(Collectors.toList());
//        Map<Integer, BookResponse> bookMap = proxy.getBooks(bookIds).getBody().stream()
//                .collect(Collectors.toMap(BookResponse::getId, b -> b));

        log.info("Aabc");
        BookResponse response = proxy.getBook(1).getBody();
        log.info("Aabc");


//        CartResponse responses = cartRepo.findByStatusAndUserId(StatusEnum.ACTIVE.name(), userId)
//                .stream()
//                .map(
//                        p -> {
//                            List<CartItemResponse> cartItemResponses = p.getItems().stream()
//                                    .map(m -> CartItemResponse.builder()
//                                            .id(m.getId())
//                                            .bookId(m.getBookId())
//                                            .price(m.getPrice())
//                                            .quantity(m.getQuantity())
//                                            .build())
//                                    .collect(Collectors.toList());
//
//                            return CartResponse.builder()
//                                    .userId(userId)
//                                    .status(p.getStatus().name())
//                                    .items(cartItemResponses)
//                                    .build();
//                        }
//                )
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());


//        return responses;
        return null;
    }

    @Override
    public void updateCart(CartItemRequest request, Long userId) {

        Cart cart = cartRepo.findByStatusAndUserId(StatusEnum.ACTIVE.name(), userId);
        if(Objects.nonNull(cart)) {
            //update cart

            Optional<CartItem> cartItem = cart.getItems().stream()
                    .filter(p -> p.getBookId().equals(request.getBookId()))
                    .findFirst();
            if(cartItem.isPresent()) {
                Integer currQuantity = cartItem.get().getQuantity();
                cartItem.get().setQuantity(currQuantity+1);

                cartItemRepo.save(cartItem.get());
            } else {
                cartItemRepo.save(CartItem.builder()
                        .cart(cart)
                        .bookId(request.getBookId())
                        .quantity(1)
                        .price(request.getPrice())
                        .build());
            }

        } else {
            //add cart

            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(CartItem.builder()
                    .bookId(request.getBookId())
                    .quantity(1)
                    .price(request.getPrice())
                    .build());

            cartRepo.save(Cart.builder()
                            .userId(userId)
                            .status(StatusEnum.ACTIVE)
                            .items(cartItems)
                    .build());
        }
    }

    @Override
    public Iterable<CartResponse> getAll() {
//        List<Cart> list = cartRepo.findAll();
//        List<CartResponse> cartResponses = cartRepo.findAll().stream()
//                .map(p -> {
//                    return CartResponse.builder()
//                            .id(p.getId())
//                            .build();
//                }).collect(Collectors.toList());
        return null;
    }

    @Override
    public Iterable<CartResponse> getByUserId(Integer id) {
        List<Cart> list = cartRepo.findAll();
        return null;
    }

    public BookResponse getBook(Integer bookId) {
        BookResponse response = proxy.getBook(bookId).getBody();
        return Objects.isNull(response) ? null : response;
    }
}
