package com.example.FirstProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.annotation.processing.Generated;
import java.util.List;

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
@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Prouducts>  prouducts;

}
