package com.example.ebookservice.payload.response;

import com.example.ebookdatamodel.entity.Category;
import com.example.ebookdatamodel.entity.Image;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
public class BookResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("author")
    private String author;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("publish_year")
    private String publishYear;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("number_sales")
    private Integer numberSales;

    @JsonProperty("description")
    private String description;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("category")
    private Category category;

    @JsonProperty("images")
    private List<Image> image;
}
