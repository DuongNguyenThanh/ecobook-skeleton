package com.example.bookservice.request;

import com.example.bookservice.model.Category;
import com.example.bookservice.model.Image;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

public class BookAddRequest {
    private String name;
    private String author;
    private String publisher;
    private String publishYear;
    private Float price;
    private Integer numberSales;
    private String description;
    private Integer quantity;
    private Category category;
    private List<Image> image;
}
