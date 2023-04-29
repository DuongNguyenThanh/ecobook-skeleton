package com.example.cartservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DelCartItemRequest {

    @JsonProperty("cart_id")
    private Integer cartId;

    @JsonProperty("cart_item_id")
    private Integer cartItemId;
}
