package com.example.ebookservice.service;

import com.example.api.model.GeneralPageResponse;
import com.example.ebookservice.payload.request.BookRequest;
import com.example.ebookservice.payload.response.BookResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    GeneralPageResponse<BookResponse> getAllBooks(Pageable pageable);

    BookResponse getBookById(Integer bookId);

    List<BookResponse> getBooksByCategory(Integer cateId);

    List<BookResponse> searchBook(String key, Float from, Float to);

    BookResponse addBook(BookRequest bookRequest);

    BookResponse updateBook(BookRequest bookRequest);

    String deleteBook(Integer bookId);
}
