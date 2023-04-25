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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/ebook", produces = "application/json")
@Slf4j
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private EntityLinks entityLinks;
    private final BookRepository bookRepo;
    private final CategoryRepository categoryRepo;

    @GetMapping("/")
    public Iterable<Book> getAll(){
        return bookRepo.findAll();
    }

    @GetMapping("/{bookId}")
    public Book getDetail(@PathVariable Integer bookId){
        Optional<Book> book = bookRepo.findById(bookId);
        if(book.isPresent()){
            return book.get();
        }
        return null;
    }

    //search book theo ten
    @GetMapping("/search")
    public Iterable<Book> searchBookByName(@RequestParam String key,@RequestParam Float from, @RequestParam Float to){
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
        return rs;
    }
    @PostMapping(consumes = "application/json")
    public Book addBook(@RequestBody BookAddRequest bookAddRequest){

        Category category = categoryRepo.getOne(bookAddRequest.getCategory().getId());

        return bookRepo.save(Book.builder()
                        .name(bookAddRequest.getName())
                        .author(bookAddRequest.getAuthor())
                        .price(bookAddRequest.getPrice())
                        .category(category)
                        .publishYear(bookAddRequest.getPublishYear())
                        .numberSales(bookAddRequest.getNumberSales())
                        .publisher(bookAddRequest.getPublisher())
                        .quantity(bookAddRequest.getQuantity())
                        .description(bookAddRequest.getDescription())
                .build());
    }

    @PutMapping("/{bookId}")
    public Book updateBook(@RequestBody Book book){
        return bookRepo.save(book);
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
