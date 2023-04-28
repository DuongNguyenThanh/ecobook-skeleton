package com.example.proxycommon.ebook.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("author")
    private String author;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("publish_year")
    private String publishYear;

    @JsonProperty("category")
    private Float price;

    @JsonProperty("category")
    private Integer numberSales;

    @JsonProperty("category")
    private String description;

    @JsonProperty("category")
    private Integer quantity;
}
