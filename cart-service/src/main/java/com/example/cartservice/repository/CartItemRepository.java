package com.example.cartservice.repository;

import com.example.cartdatamodel.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findAllByCartId(Integer id);

    List<CartItem> findByCartIdIn(Collection<Integer> cartIds);

}
