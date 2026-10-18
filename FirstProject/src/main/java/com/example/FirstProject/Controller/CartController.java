package com.example.FirstProject.Controller;

import com.example.FirstProject.Repository.CartRepo;
import com.example.FirstProject.Service.CartService;
import com.example.FirstProject.model.Cart;
import com.example.FirstProject.payload.CartDTO;
import com.example.FirstProject.util.AuthUtile;
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

    @PostMapping("/cart/product/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                   @PathVariable Integer quantity) {
CartDTO cartDTO =  cartService.addProductToCart(productId,quantity);
return new  ResponseEntity<CartDTO>(cartDTO,HttpStatus.CREATED);

    }
    @GetMapping("/cart")
    public ResponseEntity<List<CartDTO>> getCart(){
        List<CartDTO> cartDTOS=cartService.getAllCarts();
        return new  ResponseEntity<List<CartDTO>>(cartDTOS,HttpStatus.FOUND);
    }
    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById( ){
        String emailId=authUtile.loggedInEmail();
        Cart cart=cartRepo.findCartByEmail(emailId);
        Long cartId= cart.getCartId();
        CartDTO cartDTO=cartService.getCart(emailId,cartId);
        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
    }
    @PutMapping("/cart/product/{product_id}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct (@PathVariable Long product_id
            , @PathVariable String operation){
       CartDTO cartDTO= cartService.updateProductQuantityInCart(product_id,
                operation.equalsIgnoreCase("delete")?-1:1);
        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);

    }
    @Transactional
    @DeleteMapping("/carts/{cart_id}/product/{product_id}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cart_id, @PathVariable Long product_id){
        String status=cartService.deleteProductFromCart(cart_id,product_id);
        return new ResponseEntity<String>(status,HttpStatus.OK);
    }
}
