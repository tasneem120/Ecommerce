package com.example.FirstProject.Controller;
import com.example.FirstProject.Service.CategoryService;
import com.example.FirstProject.config.AppConstant;
import com.example.FirstProject.payload.CategoryDTO;
import com.example.FirstProject.payload.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class Categorycontroller {
    @Autowired
   private CategoryService categoryService;



    @GetMapping("/public/category")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "PageNumber",defaultValue =AppConstant.PAGE_NUMBER,required = false ) Integer pageNumber,
            @RequestParam(name = "PageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy" ,defaultValue = AppConstant.SORT_CATEGORY_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstant.SORT_DIR,required = false) String sortOrder

            ) {

        CategoryResponse categoryResponse =
                categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
    @PostMapping("/public/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
       CategoryDTO savedCategory=categoryService.CreateCategory(categoryDTO);
 return new ResponseEntity<>(savedCategory,HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO categoryDTO =  categoryService.deleteCategory(id);
            return    ResponseEntity.ok(categoryDTO);
    }
//        @RequestMapping(value ="/admin/category/{id}",method = RequestMethod.PUT )
    @PutMapping("/admin/category/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
           CategoryDTO category1=    categoryService.updateCategory(categoryDTO,id);
            return new   ResponseEntity<>( category1,HttpStatus.OK);
    }
}
