package com.example.FirstProject.Controller;

import com.example.FirstProject.Service.ProductService;
import com.example.FirstProject.config.AppConstant;
import com.example.FirstProject.model.Product;
import com.example.FirstProject.payload.ProductDTO;
import com.example.FirstProject.payload.ProductResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductControl {
    @Autowired
    ProductService productService;
    @Tag(name="Product API",description = "APIS for managing product")

    @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody Product products,@Parameter(description = "ID for category to add product in it") @PathVariable Long categoryId   ) {
        ProductDTO productDTO = productService.addProduct(products,categoryId);
        return new ResponseEntity<>(productDTO,HttpStatus.CREATED);

    }
    @Tag(name="Product API",description = "APIS for managing product")

    @GetMapping("public/product")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber ,
            @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE,required = false)  Integer pageSize ,
            @RequestParam(value = "sortBy" , defaultValue = AppConstant.SORT_PRODUCT_BY,required = false) String  sortBy ,
            @RequestParam(value = "sortOrder" ,defaultValue = AppConstant.SORT_DIR,required = false) String sortOrder

    ) {
        ProductResponse prouducts=  productService.getAllProduct(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(prouducts,HttpStatus.OK);
    }
    @Tag(name="Product API",description = "APIS for managing product")

    @GetMapping("public/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> findProductsByCategory(
           @Parameter(description = "CategoryId to find product") @PathVariable Long categoryId,
      @RequestParam(value = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber ,
            @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE,required = false)  Integer pageSize ,
            @RequestParam(value = "sortBy" , defaultValue = AppConstant.SORT_PRODUCT_BY,required = false) String  sortBy ,
            @RequestParam(value = "sortOrder" ,defaultValue = AppConstant.SORT_DIR,required = false) String sortOrder
    ) {
        ProductResponse  productResponse=productService.findProductsByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @Tag(name="Product API",description = "APIS for managing product")

    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> findByProductName(
            @Parameter(description = "Keyword to find product")
            @PathVariable String keyword,
            @RequestParam(value = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber ,
            @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE,required = false)  Integer pageSize ,
            @RequestParam(value = "sortBy" , defaultValue = AppConstant.SORT_PRODUCT_BY,required = false) String  sortBy ,
            @RequestParam(value = "sortOrder" ,defaultValue = AppConstant.SORT_DIR,required = false) String sortOrder

    ) {
       ProductResponse productResponse=  productService.findProductsByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
       return new ResponseEntity<>(productResponse,HttpStatus.FOUND);
    }
    @Tag(name="Product API",description = "APIS for managing product")

    @PutMapping("/admin/products/{productId}")
    public  ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "ProductId to update product")
            @PathVariable  Long productId,
                                                     @RequestBody Product product) {
     ProductDTO productDTO=   productService.updateProduct(productId, product);
return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
    @Tag(name="Product API",description = "APIS for managing product")

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(
            @Parameter(description = "ProductId to delete product")

            @PathVariable  Long productId) {
      ProductDTO productDTO=  productService.deleteProduct(productId);
      return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
    @Tag(name="Product API",description = "APIS for managing product")

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage
            (
                    @Parameter(description = "ProductId to update image")
                    @PathVariable  Long productId,@RequestParam("image") MultipartFile image    ) throws IOException {
      ProductDTO productDTO=  productService.updateProductImage(productId,image);
      return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
}
