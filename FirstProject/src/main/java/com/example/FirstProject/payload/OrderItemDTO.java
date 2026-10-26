package com.example.FirstProject.payload;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private double orderProductPrice;
    private  PaymentDTO paymentDTO;
    private Long orderItemId;
    private Integer quantity;
    private double discount;

}
