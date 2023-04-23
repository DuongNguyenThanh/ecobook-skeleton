package com.example.bookservice.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryAddRequest {
    private String name;
    private String description;
}
