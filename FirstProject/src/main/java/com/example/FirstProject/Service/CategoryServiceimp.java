package com.example.FirstProject.Service;

import com.example.FirstProject.Reposatery.CategoryRepo;
import com.example.FirstProject.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceimp implements CategoryService{
//    private List<Category> categories=new ArrayList<>();
    @Autowired
    CategoryRepo categoryrepo;
    @Override
    public List<Category> getAllCategories() {
        return categoryrepo.findAll();
    }

    @Override
    public void CreateCategory(Category category) {
categoryrepo.save(category);
    }

    @Override
    public String deleteCategory(Long id) {

        Category category = categoryrepo.findById(id) .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        categoryrepo.delete(category);

        return " Category Deleted Successfully Id "+category.getCategoryid();
    }

    @Override
    public Category updateCategory(Category category, Long id) {

Category savedCategory= categoryrepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, " Resource Not Found"));
category.setCategoryid(id);
savedCategory=categoryrepo.save(category);
return savedCategory;



}}
