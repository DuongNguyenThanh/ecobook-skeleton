package com.example.commentservice.controller;

import com.example.commentservice.model.BookReview;
import com.example.commentservice.request.AddReviewRequest;
import com.example.commentservice.service.BookReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/book-review", produces = "application/json")
public class BookReviewController {
    private BookReviewService bookReviewService;


    @GetMapping("/")
    public Iterable<BookReview> getAll(){
        return bookReviewService.getAll();
    }
    @GetMapping("/{bookId}")
    public Iterable<BookReview> getByBookId(@PathVariable Integer bookId){
        return bookReviewService.getByBookId(bookId);
    }
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookReview addBook(@RequestBody AddReviewRequest reviewRequest){
        return bookReviewService.addReview(reviewRequest);
    }
    @PutMapping("/{bookId}")
    public BookReview updateBook(@RequestBody BookReview bookReview){
        return bookReviewService.updateReview(bookReview);
    }
    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Integer bookId){
        bookReviewService.deleteReview(bookId);
    }
}