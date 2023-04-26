package com.example.cartservice.data;

import com.example.cartservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    Iterable<CartItem> findAllByCartId(Integer id);

}
