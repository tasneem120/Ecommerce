package com.example.FirstProject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long orderId;
    private  String email;
   private List<OrderItemDTO> orderItems;
    private PaymentDTO payment;
    private Double totalAmount;
    private LocalDate orderDate;
    private Double orderTotal;
    private String orderStatus;
    private Long addressId;
}
