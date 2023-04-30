package com.example.ebookservice.service;

import com.example.ebookservice.payload.request.BookRequest;
import com.example.ebookservice.payload.response.BookResponse;

import java.util.List;

public interface BookService {

    List<BookResponse> getAllBooks();

    BookResponse getBookById(Integer bookId);

    List<BookResponse> getBooksByCategory(Integer cateId);

    List<BookResponse> searchBook(String key, Float from, Float to);

    BookResponse addBook(BookRequest bookRequest);

    BookResponse updateBook(BookRequest bookRequest);

    String deleteBook(Integer bookId);
}
