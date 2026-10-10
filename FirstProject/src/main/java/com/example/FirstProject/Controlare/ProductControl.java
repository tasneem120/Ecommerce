package com.example.FirstProject.Controlare;

import com.example.FirstProject.Service.ProductService;
import com.example.FirstProject.config.AppConstant;
import com.example.FirstProject.model.Prouducts;
import com.example.FirstProject.payload.ProductDTO;
import com.example.FirstProject.payload.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductControl {
    @Autowired
    ProductService productService;
    @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody Prouducts products, @PathVariable Long categoryId   ) {
        ProductDTO productDTO = productService.addProduct(products,categoryId);
        return new ResponseEntity<>(productDTO,HttpStatus.CREATED);

    }
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
    @GetMapping("public/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> findProductsByCategory(
            @PathVariable Long categoryId,
      @RequestParam(value = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber ,
            @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE,required = false)  Integer pageSize ,
            @RequestParam(value = "sortBy" , defaultValue = AppConstant.SORT_PRODUCT_BY,required = false) String  sortBy ,
            @RequestParam(value = "sortOrder" ,defaultValue = AppConstant.SORT_DIR,required = false) String sortOrder
    ) {
        ProductResponse  productResponse=productService.findProductsByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> findByProductName(
            @PathVariable String keyword,
            @RequestParam(value = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber ,
            @RequestParam(value = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE,required = false)  Integer pageSize ,
            @RequestParam(value = "sortBy" , defaultValue = AppConstant.SORT_PRODUCT_BY,required = false) String  sortBy ,
            @RequestParam(value = "sortOrder" ,defaultValue = AppConstant.SORT_DIR,required = false) String sortOrder

    ) {
       ProductResponse productResponse=  productService.findProductsByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
       return new ResponseEntity<>(productResponse,HttpStatus.FOUND);
    }
    @PutMapping("/admin/products/{productId}")
    public  ResponseEntity<ProductDTO> updateProduct(@PathVariable  Long productId,
                                                     @RequestBody Prouducts prouducts ) {
     ProductDTO productDTO=   productService.updateProduct(productId,prouducts);
return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable  Long productId) {
      ProductDTO productDTO=  productService.deleteProduct(productId);
      return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage
            (@PathVariable  Long productId,@RequestParam("image") MultipartFile image    ) throws IOException {
      ProductDTO productDTO=  productService.updateProductImage(productId,image);
      return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
}
