package com.example.FirstProject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private String description;
    private double specialPrice;
    private  double discount;
    private  String image;

}
