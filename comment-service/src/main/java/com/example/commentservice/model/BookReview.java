package com.example.commentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String context;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "user_id")
    private Integer userId;
}
