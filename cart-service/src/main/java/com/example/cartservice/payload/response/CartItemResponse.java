package com.example.cartservice.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CartItemResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("book_id")
    private Integer bookId;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("quantity")
    private Integer quantity;
}
