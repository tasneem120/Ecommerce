package com.example.FirstProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Email
    @Column(nullable = false)
    private  String email;
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItems>  orderItems=new ArrayList<>();
    private LocalDate orderDate;
    private Double orderTotal;
    private String orderStatus;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private  Address addresses;
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

}
