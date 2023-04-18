package com.example.paymentservice.data;

import com.example.paymentservice.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentInfo,Integer> {
}
