package com.example.FirstProject.Controller;
import com.example.FirstProject.Service.CategoryService;
import com.example.FirstProject.config.AppConstant;
import com.example.FirstProject.payload.CategoryDTO;
import com.example.FirstProject.payload.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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


@Tag(name="Category API",description = "APIS for managing categories")
@Operation(summary = "Get All Categories",description = "get categories")
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
    @Tag(name="Category API",description = "APIS for managing categories")

    @PostMapping("/public/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
       CategoryDTO savedCategory=categoryService.CreateCategory(categoryDTO);
 return new ResponseEntity<>(savedCategory,HttpStatus.CREATED);
    }
    @Tag(name="Category API",description = "APIS for managing categories")

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(
            @Parameter(description = "CategoryId to delete category ")

            @PathVariable Long id) {
        CategoryDTO categoryDTO =  categoryService.deleteCategory(id);
            return    ResponseEntity.ok(categoryDTO);
    }
//        @RequestMapping(value ="/admin/category/{id}",method = RequestMethod.PUT )
@Tag(name="Category API",description = "APIS for managing categories")

    @PutMapping("/admin/category/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                      @Parameter(description = "CategoryId to update category ")
                                                      @PathVariable Long id) {
           CategoryDTO category1=    categoryService.updateCategory(categoryDTO,id);
            return new   ResponseEntity<>( category1,HttpStatus.OK);
    }
}
