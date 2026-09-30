package com.example.FirstProject.Reposatery;

import com.example.FirstProject.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    Category findByCategoryName( String categoryname);
}
