package com.example.FirstProject.Controller;

import com.example.FirstProject.Repository.CartRepo;
import com.example.FirstProject.Service.CartService;
import com.example.FirstProject.model.Cart;
import com.example.FirstProject.payload.CartDTO;
import com.example.FirstProject.util.AuthUtile;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private AuthUtile authUtile;
    @Autowired
    private CartRepo cartRepo;
    @Tag(name="Cart API",description = "APIS for managing Cart")

    @PostMapping("/cart/product/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@Parameter(description = "ProductId to add product to cart") @PathVariable Long productId,
                                                    @Parameter(description = "Quantity for product to add into cart")           @PathVariable Integer quantity) {
CartDTO cartDTO =  cartService.addProductToCart(productId,quantity);
return new  ResponseEntity<CartDTO>(cartDTO,HttpStatus.CREATED);

    }
    @Tag(name="Cart API",description = "APIS for managing Cart")

    @GetMapping("/cart")
    public ResponseEntity<List<CartDTO>> getCart(){
        List<CartDTO> cartDTOS=cartService.getAllCarts();
        return new  ResponseEntity<List<CartDTO>>(cartDTOS,HttpStatus.FOUND);
    }
    @Tag(name="Cart API",description = "APIS for managing Cart")

    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById( ){
        String emailId=authUtile.loggedInEmail();
        Cart cart=cartRepo.findCartByEmail(emailId);
        Long cartId= cart.getCartId();
        CartDTO cartDTO=cartService.getCart(emailId,cartId);
        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
    }
    @Tag(name="Cart API",description = "APIS for managing Cart")

    @PutMapping("/cart/product/{product_id}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct (
            @Parameter(description = "ProductId to update product")
            @PathVariable Long product_id
            ,@Parameter(description = "Operation for update **delete** or **add**") @PathVariable String operation){
       CartDTO cartDTO= cartService.updateProductQuantityInCart(product_id,
                operation.equalsIgnoreCase("delete")?-1:1);
        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);

    }
    @Transactional
    @Tag(name="Cart API",description = "APIS for managing Cart")

    @DeleteMapping("/carts/{cart_id}/product/{product_id}")
    public ResponseEntity<String> deleteProductFromCart(
            @Parameter(description = "CartId to delete product ")

            @PathVariable Long cart_id,
            @Parameter(description = "ProductId to delete product from cart")
            @PathVariable Long product_id){
        String status=cartService.deleteProductFromCart(cart_id,product_id);
        return new ResponseEntity<String>(status,HttpStatus.OK);
    }
}
