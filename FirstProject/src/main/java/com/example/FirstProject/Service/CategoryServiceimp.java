package com.example.FirstProject.Service;

import com.example.FirstProject.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceimp implements CategoryService{
    private List<Category> categories=new ArrayList<>();
    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void CreateCategory(Category category) {
categories.add(category);
    }

    @Override
    public String deleteCategory(Long id) {
        Category category= (Category) categories.stream().filter(c->c.getCategoryid().equals(id))
                .findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, " Resource Not Found"));
//        if(category==null){
//            return "Category Not Found";
//        }
        categories.remove(category);
        return " Category Deleted Successfully Id"+category.getCategoryid();
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        Optional<Category> optionalCategorycategory= categories.stream().filter(c->c.getCategoryid()
                .equals(id)).findFirst();
        if(optionalCategorycategory.isPresent()){
            Category categoryex=optionalCategorycategory.get();
            categoryex.setCategoryname(category.getCategoryname());
            return  categoryex ;

        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " Category Not Found");
        }

    }


}
