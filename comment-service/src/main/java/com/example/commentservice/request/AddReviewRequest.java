package com.example.commentservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewRequest {
    private String context;
    private Date createdAt;
    private Integer productId;
    private Integer userId;
}
