package com.example.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    @JsonProperty("price")
    private float price;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("book_id")
    private int bookId;
}
