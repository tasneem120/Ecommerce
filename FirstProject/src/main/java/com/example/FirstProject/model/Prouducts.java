package com.example.FirstProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Prouducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotBlank
    @Size(min = 3, message = "product name must contain at least 3 characters")
    private String productName;
    @NotBlank
    @Size(min = 6, message = "product name must contain at least 6 characters")

    private String description;
    private  Integer quantity;
    private double price;
    private  double specialPrice;
    private  double discount;
    private  String image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;
}
