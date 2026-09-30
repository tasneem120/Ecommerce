package com.example.FirstProject.Service;

import com.example.FirstProject.model.Category;
import com.example.FirstProject.payload.CategoryDTOReq;
import com.example.FirstProject.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryDTOReq CreateCategory(CategoryDTOReq categoryDTO);
    CategoryDTOReq deleteCategory(Long id);

    CategoryDTOReq updateCategory(CategoryDTOReq categoryDTOReq, Long id);
}
