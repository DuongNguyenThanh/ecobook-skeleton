package com.example.proxycommon.ebook.proxy;

import com.example.proxycommon.ebook.payload.response.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "EBOOK-SERVICE")
public interface EbookServiceProxy {

    @GetMapping("/api/ebook/{bookId}")
    ResponseEntity<BookResponse> getBook(
            @PathVariable("bookId") Integer bookId
    );

    @GetMapping("/get-in-list-ids")
    ResponseEntity<List<BookResponse>> getBooks(
            @RequestBody List<Integer> ids
    );
}
