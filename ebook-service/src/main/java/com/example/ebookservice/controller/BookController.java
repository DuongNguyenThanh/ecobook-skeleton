package com.example.ebookservice.controller;

import com.example.ebookdatamodel.entity.Book;
import com.example.ebookdatamodel.entity.Category;
import com.example.ebookservice.repository.BookRepository;
import com.example.ebookservice.payload.request.BookAddRequest;
import com.example.ebookservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/ebook", produces = "application/json")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepo;
    private final CategoryRepository categoryRepo;

    @GetMapping("/all-book")
    public Iterable<Book> getAll(){
        return bookRepo.findAll();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Integer bookId){
        Optional<Book> book = bookRepo.findById(bookId);
        if(book.isPresent()){
            return ResponseEntity.ok(book.get());
        }
        return null;
    }

    @GetMapping("/book-cate")
    public ResponseEntity<List<Book>> getBookByCategory(
            @RequestParam(name = "cate-id") Integer cateId
    ) {
        List<Book> books = bookRepo.findAllByCategoryId(cateId);
        if(!books.isEmpty()){
            return ResponseEntity.ok(books);
        }
        return null;
    }

    //search book theo ten
    @GetMapping("/search")
    public ResponseEntity<Iterable<Book>> searchBookByName(
            @RequestParam String key,
            @RequestParam Float from,
            @RequestParam Float to
    ) {
//        if(from.isNaN() && to.isNaN()) {
//            return null;
//        }
        ArrayList<Book> rs = new ArrayList<>();
        Iterable<Book> books = bookRepo.findByNameContaining(key);
        for (Book b:books) {
            if(b.getPrice()<=to && b.getPrice() >= from){
                rs.add(b);
            }
        }
        return ResponseEntity.ok(rs);
    }
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody BookAddRequest bookAddRequest){

        Category category = categoryRepo.getOne(bookAddRequest.getCategory().getId());

        return ResponseEntity.ok(bookRepo.save(Book.builder()
                        .name(bookAddRequest.getName())
                        .author(bookAddRequest.getAuthor())
                        .price(bookAddRequest.getPrice())
                        .category(category)
                        .publishYear(bookAddRequest.getPublishYear())
                        .numberSales(bookAddRequest.getNumberSales())
                        .publisher(bookAddRequest.getPublisher())
                        .quantity(bookAddRequest.getQuantity())
                        .description(bookAddRequest.getDescription())
                .build()));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        return ResponseEntity.ok(bookRepo.save(book));
    }
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Integer bookId){
        try{
            bookRepo.deleteById(bookId);
        }catch (EmptyResultDataAccessException e){
            log.info(e.getMessage());
            return "fail";
        }
        return "success";
    }
}
