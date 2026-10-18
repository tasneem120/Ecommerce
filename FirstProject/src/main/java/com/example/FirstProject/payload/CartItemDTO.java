package com.example.FirstProject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private CartDTO cartDTO;
    private Integer quantity;
    private Double productPrice;
    private Double discount;
    private ProductDTO productDTO;

}
