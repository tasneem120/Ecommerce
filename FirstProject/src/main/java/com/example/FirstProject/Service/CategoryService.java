package com.example.FirstProject.Service;

import com.example.FirstProject.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void CreateCategory(Category category);
    String deleteCategory(Long id);

    Category updateCategory(Category category, Long id);
}
