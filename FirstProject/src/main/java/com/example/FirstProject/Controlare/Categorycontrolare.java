package com.example.FirstProject.Controlare;
import com.example.FirstProject.Service.CategoryService;
import com.example.FirstProject.config.AppConstant;
import com.example.FirstProject.model.Category;
import com.example.FirstProject.payload.CategoryDTOReq;
import com.example.FirstProject.payload.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class Categorycontrolare {
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
    public ResponseEntity< CategoryDTOReq> createCategory(@Valid @RequestBody CategoryDTOReq categoryDTOReq ) {
       CategoryDTOReq savedCategory=categoryService.CreateCategory(categoryDTOReq);
 return new ResponseEntity<>(savedCategory,HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<CategoryDTOReq> deleteCategory(@PathVariable Long id) {
        CategoryDTOReq categoryDTOReq=  categoryService.deleteCategory(id);
            return    ResponseEntity.ok(categoryDTOReq);
    }
//        @RequestMapping(value ="/admin/category/{id}",method = RequestMethod.PUT )
    @PutMapping("/admin/category/{id}")
    public ResponseEntity<CategoryDTOReq> updateCategory( @Valid @RequestBody CategoryDTOReq categoryDTOReq, @PathVariable Long id) {
           CategoryDTOReq category1=    categoryService.updateCategory(categoryDTOReq,id);
            return new   ResponseEntity<>( category1,HttpStatus.OK);
    }
}
