package com.example.commentservice.data;

import com.example.commentservice.model.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview,Integer> {
    Iterable<BookReview> getAllByBookId(Integer bookId);
}
