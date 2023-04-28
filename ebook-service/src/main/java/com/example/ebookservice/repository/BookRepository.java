package com.example.ebookservice.repository;

import com.example.ebookdatamodel.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByNameContaining(String keyword);

    @Query(nativeQuery = true,
            value = "SELECT b.* FROM book b WHERE b.category_id = :cateId")
    List<Book> findAllByCategoryId(Integer cateId);
}