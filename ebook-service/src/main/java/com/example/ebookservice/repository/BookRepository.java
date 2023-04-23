package com.example.ebookservice.repository;

import com.example.ebookdatamodel.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Iterable<Book> findByNameContaining(String keyword);
    @Query(value = "SELECT b FROM book b WHERE b.price >= :fromPrice AND b.price <= :toPrice", nativeQuery = true)
    Iterable<Book> findByPriceRange(@Param("fromPrice") Float fromPrice, @Param("toPrice") Float toPrice);
}