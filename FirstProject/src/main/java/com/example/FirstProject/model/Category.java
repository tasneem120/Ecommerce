package com.example.FirstProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.annotation.processing.Generated;
@Entity(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long categoryid;
@Size(min=5,message = "Category must be at least 5 characters")
@NotBlank
private String categoryName;


}
