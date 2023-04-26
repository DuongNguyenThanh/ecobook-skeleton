package com.example.orderservice.repository;

import java.util.List;

import com.example.orderdatamodel.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.customer_id = (:customer_id)")
    List<Order> findByCustomer_id(Integer customer_id);    
}
