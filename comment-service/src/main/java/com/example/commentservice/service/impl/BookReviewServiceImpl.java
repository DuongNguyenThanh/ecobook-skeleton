package com.example.commentservice.service.impl;

import com.example.commentdatamodel.entity.BookReview;
import com.example.commentservice.repository.BookReviewRepository;
import com.example.commentservice.request.AddReviewRequest;
import com.example.commentservice.service.BookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.stereotype.Service;

@Service
public class BookReviewServiceImpl implements BookReviewService {
    @Autowired
    private EntityLinks entityLinks;
    private BookReviewRepository bookReviewRepository;

    public BookReviewServiceImpl(BookReviewRepository bookReviewRepository) {
        this.bookReviewRepository = bookReviewRepository;
    }

    @Override
    public Iterable<BookReview> getAll() {
        return bookReviewRepository.findAll();
    }

    @Override
    public Iterable<BookReview> getByBookId(Integer bookId) {
        return bookReviewRepository.getAllByProductId(bookId);
    }

    @Override
    public BookReview addReview(Long userId, AddReviewRequest reviewRequest) {
        return bookReviewRepository.save( BookReview.builder()
                        .context(reviewRequest.getContext())
                        .productId(reviewRequest.getProductId())
                        .userId(userId)
                .build());
    }

    @Override
    public BookReview updateReview(BookReview bookReview) {
        return bookReviewRepository.save(bookReview);
    }

    @Override
    public void deleteReview(Integer bookId) {
        bookReviewRepository.deleteById(bookId);
    }
}
