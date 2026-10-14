package com.example.FirstProject.Repository;

import com.example.FirstProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    Category findByCategoryName( String categoryname);
}
