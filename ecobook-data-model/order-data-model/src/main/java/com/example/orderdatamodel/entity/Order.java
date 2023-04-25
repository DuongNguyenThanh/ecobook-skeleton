package com.example.orderdatamodel.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sub_total")
    private Float subTotal;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
