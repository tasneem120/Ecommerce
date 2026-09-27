package com.example.FirstProject.Controlare;

import com.example.FirstProject.Service.CategoryService;
import com.example.FirstProject.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Categorycontrolare {
    @Autowired
   private CategoryService categoryService;



    @GetMapping("/public/category")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    @PostMapping("/public/category")
    public String createCategory(@RequestBody Category category ) {
       categoryService.CreateCategory(category);
        return " Category created successfully";
    }
    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
        String status=    categoryService.deleteCategory(id);
            return    ResponseEntity.ok(status);
        }catch(ResponseStatusException e) {
            return new ResponseEntity<>( e.getReason(),e.getStatusCode());
        }

    }
//        @RequestMapping(value ="/admin/category/{id}",method = RequestMethod.PUT )
    @PutMapping("/admin/category/{id}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long id) {

        try {
           Category category1=    categoryService.updateCategory(category,id);
            return new   ResponseEntity<>("Category updated successfully with ID : "+id,HttpStatus.OK);
        }catch(ResponseStatusException e) {
            return new ResponseEntity<>( e.getReason(),e.getStatusCode());
        }
    }
}
