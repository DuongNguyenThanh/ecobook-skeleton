package com.example.ebookservice.payload.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageAddRequest {
    private Integer id;
    private String name;
    private String img;
}
