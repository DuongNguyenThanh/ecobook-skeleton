package com.example.ebookservice.controller;

import com.example.ebookdatamodel.entity.Book;
import com.example.ebookservice.repository.BookRepository;
import com.example.ebookservice.payload.request.BookAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/book", produces = "application/json")
@Slf4j
public class BookController {
    @Autowired
    private EntityLinks entityLinks;
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @GetMapping("/")
    public Iterable<Book> getAll(){
        return bookRepository.findAll();
    }
    @GetMapping("/{bookId}")
    public Book getDetail(@PathVariable Integer bookId){
        Optional<Book> book = bookRepository.findById(bookId);
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
        Iterable<Book> books = bookRepository.findByNameContaining(key);
        for (Book b:books) {
            if(b.getPrice()<=to && b.getPrice() >= from){
                rs.add(b);
            }
        }
        return rs;
    }
    @PostMapping(consumes = "application/json")
    public Book addBook(@RequestBody BookAddRequest bookAddRequest){
        //can sua...
        Book book = new Book();
        return bookRepository.save(book);
    }
    @PutMapping("/{bookId}")
    public Book updateBook(@RequestBody Book book){
        return bookRepository.save(book);
    }
    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable Integer bookId){
        try{
            bookRepository.deleteById(bookId);
        }catch (EmptyResultDataAccessException e){
            log.info(e.getMessage());
            return "fail";
        }
        return "success";
    }
}
