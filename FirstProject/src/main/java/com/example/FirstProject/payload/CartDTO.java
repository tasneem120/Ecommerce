package com.example.FirstProject.payload;

import com.example.FirstProject.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private  Long cartId;
    private double totalPrice;
    private List<ProductDTO> products;
}
