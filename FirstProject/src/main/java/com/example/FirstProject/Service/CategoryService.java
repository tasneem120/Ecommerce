package com.example.FirstProject.Service;

import com.example.FirstProject.payload.CategoryDTO;
import com.example.FirstProject.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    CategoryDTO CreateCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id);
}
