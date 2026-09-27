package com.example.FirstProject.Reposatery;

import com.example.FirstProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
