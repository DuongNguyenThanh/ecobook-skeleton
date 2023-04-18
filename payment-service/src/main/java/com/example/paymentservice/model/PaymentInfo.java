package com.example.paymentservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Data
@NoArgsConstructor
@Entity
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "payment_date")
    private Date paymentDate;
    @Column(name = "amount")
    private Float amount;
    @Column(name = "payment_type")
    private String paymentType;
    @Column(name = "status")
    private String status;
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "payer_id")
    private String payerId;
    @Column(name = "paypal_id")
    private String paypalId;

}
