package com.example.commentservice.service;

import com.example.commentservice.model.BookReview;
import com.example.commentservice.request.AddReviewRequest;

public interface BookReviewService {
    Iterable<BookReview> getAll();
    Iterable<BookReview> getByBookId(Integer bookId);
    BookReview addReview(AddReviewRequest reviewRequest);
    BookReview updateReview(BookReview bookReview);
    void deleteReview(Integer bookId);
}
