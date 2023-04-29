package com.example.cartservice.repository;

import com.example.cartdatamodel.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserId(Integer id);

    @Query(nativeQuery = true,
            value = "SELECT c.* " +
                    "FROM cart c " +
                    "WHERE c.user_id = :userId AND c.status = :status ")
    Cart findByStatusAndUserId(String status, Long userId);
}
