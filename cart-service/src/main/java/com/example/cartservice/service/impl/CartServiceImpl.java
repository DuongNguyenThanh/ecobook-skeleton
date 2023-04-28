package com.example.cartservice.service.impl;

import com.example.cartdatamodel.entity.Cart;
import com.example.cartdatamodel.entity.CartItem;
import com.example.cartdatamodel.entity.enumtype.StatusEnum;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

        List<CartResponse> responses = cartRepo.findAllByStatusAndUserId(StatusEnum.ACTIVE.name(), userId)
                .stream()
                .map(
                        p -> {
                            List<CartItemResponse> cartItemResponses = p.getItems().stream()
                                    .map(m -> CartItemResponse.builder()
                                            .id(m.getId())
                                            .bookId(m.getBookId())
                                            .price(m.getPrice())
                                            .quantity(m.getQuantity())
                                            .build())
                                    .collect(Collectors.toList());

                            return CartResponse.builder()
                                    .userId(userId)
                                    .status(p.getStatus().name())
                                    .items(cartItemResponses)
                                    .build();
                        }
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        log.info("Aabc");
        BookResponse response = getBook(1);
        log.info("Aabc");

        return responses;
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
