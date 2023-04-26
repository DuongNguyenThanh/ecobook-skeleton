package com.example.cartservice.data;

import com.example.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    Iterable<Cart> findAllByUserId(Integer id);
}
