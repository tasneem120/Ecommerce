package com.example.FirstProject.Service;

import com.example.FirstProject.Repository.CartItemRepo;
import com.example.FirstProject.Repository.CartRepo;
import com.example.FirstProject.Repository.ProductRepo;
import com.example.FirstProject.exception.APIException;
import com.example.FirstProject.exception.ResourceNotFoundException;
import com.example.FirstProject.model.Cart;
import com.example.FirstProject.model.CartItem;
import com.example.FirstProject.model.Product;
import com.example.FirstProject.payload.CartDTO;
import com.example.FirstProject.payload.ProductDTO;
import com.example.FirstProject.util.AuthUtile;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImp implements CartService{
@Autowired
    CartRepo cartRepo;
@Autowired
ProductRepo productRepo;
@Autowired
ModelMapper modelMapper;
@Autowired
CartItemRepo cartItemRepo;
@Autowired
AuthUtile authUtile;
    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
Cart cart=createCart();
        Product product=productRepo.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product",
                        "productId",productId)
        );
      CartItem cartItem= cartItemRepo.findCartItemByProductIdAndCartId
              (cart.getCartId(), product.getProductId());

      if (cartItem!=null)
          throw new APIException("Product"+product.getProductName()+
                  " already exists");
          if (product.getQuantity()==0 )
              throw new APIException(product.getProductName()+"isn't available");
if (product.getQuantity()<quantity)
    throw new APIException("Please , make an order of the "+
            product.getProductName()+"less than or equal to the quantity "
            +product.getQuantity()+".");
CartItem newCartItem=new CartItem();
newCartItem.setProduct(product);
newCartItem.setCart(cart);
newCartItem.setQuantity(quantity);
newCartItem.setDiscount(product.getDiscount());
newCartItem.setProductPrice(product.getSpecialPrice());

cartItemRepo.save(newCartItem);
        cart.getItems().add(newCartItem);
product.setQuantity(product.getQuantity());
cart. setTotalPrice(cart.getTotalPrice()+(product.getSpecialPrice()*quantity));
cartRepo.save(cart);
CartDTO cartDTO= modelMapper.map(cart,CartDTO.class);
        List<CartItem> cartItems=cart.getItems();
        Stream<ProductDTO> productDTOStream=cartItems.stream()
                .map(c-> {
                    ProductDTO productDTO=modelMapper.map(c.getProduct(),ProductDTO.class);
                    productDTO.setQuantity(c.getQuantity());
                    return productDTO;
                });
        cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
List<Cart>  carts=cartRepo.findAll();
if (carts.isEmpty()){
    throw new APIException("No carts exist");
}
List<CartDTO> cartDTOS=   carts.stream().map(
      cart -> {
          CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
          List<ProductDTO> productDTOS = cart.getItems().stream().map(
                          p ->
                          {ProductDTO productDTO = modelMapper.map(p.getProduct(), ProductDTO.class);
                              productDTO.setQuantity(p.getQuantity());
                            return productDTO;
                          })
                  .collect(Collectors.toList());
          cartDTO.setProducts(productDTOS);
          return cartDTO;
      } ).collect(Collectors.toList());



       return cartDTOS;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart=cartRepo.findCartByEmailAndCartId(emailId,cartId);
        if (cart==null){
            throw new ResourceNotFoundException("Cart","cartId",cartId);
        }
        CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);
        cart.getItems().forEach(cartItem -> {
            cartItem.getProduct().setQuantity(cartItem.getQuantity());
        });
List<ProductDTO> products=cart.getItems().stream().map(
        p->modelMapper.map(p.getProduct(),ProductDTO.class)
).toList();
cartDTO.setProducts(products);
        return cartDTO;
    }

    @Override
    @Transactional
    public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {
       String emailId= authUtile.loggedInEmail();
       Cart uerCart=cartRepo.findCartByEmail(emailId);
       Long cartId=uerCart.getCartId();
        Cart cart=cartRepo.findById(cartId).orElseThrow(
                ()->new ResourceNotFoundException("Cart","cartId",cartId)
        );
        Product product=productRepo.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product","productId",productId)
        );
        if (product.getQuantity()==0 )
            throw new APIException(product.getProductName()+"isn't available");
        if (product.getQuantity()<quantity)
            throw new APIException("Please , make an order of the "+
                    product.getProductName()+"less than or equal to the quantity "
                    +product.getQuantity()+".");
CartItem cartItem=cartItemRepo.findCartItemByProductIdAndCartId( cartId,productId);
if (cartItem==null){
    throw new APIException(" Product "+product.getProductName()+" doesn't exist");
}
        int newQuantity=cartItem.getQuantity()+quantity;
        if (newQuantity<0){
            throw new APIException("The resulting quantity cannot be negative");
        }
        if (newQuantity==0){
            deleteProductFromCart(cartId,productId);
        }else {
            cartItem.setProductPrice(product.getSpecialPrice());
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setDiscount(product.getDiscount());
            cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getProductPrice() * quantity));
            cartRepo.save(cart);
        }
CartItem updatedCartItem=cartItemRepo.save(cartItem);
if (updatedCartItem.getQuantity()==0){
    cartItemRepo.deleteById(updatedCartItem.getCartItemId());
}

CartDTO cartDTO= modelMapper.map(cart,CartDTO.class);
List<CartItem> cartItems=cart.getItems();
Stream<ProductDTO> productDTOStream=cartItems.stream().map(
        item-> {

            ProductDTO productDTO=modelMapper.map(item.getProduct(),ProductDTO.class);
            productDTO.setQuantity(item.getQuantity());
            return productDTO;
        }
);
cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        CartItem cartItem = cartItemRepo.findCartItemByProductIdAndCartId(cartId, productId);

        if (cartItem == null) {
            throw new ResourceNotFoundException("Product", "productId", productId);
        }

        cart.setTotalPrice(cart.getTotalPrice() -
                (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItemRepo.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart !!!";
    }

    @Override
    public void updateProductInCart(Long cartId, Long productId) {
        Cart cart=cartRepo.findById(cartId).orElseThrow(
                ()->new ResourceNotFoundException("Cart","cartId",cartId)
        );
        Product product=productRepo.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product","productId",productId)
        );
        CartItem cartItem=cartItemRepo.findCartItemByProductIdAndCartId(productId,cartId);
        if (cartItem==null){
            throw new APIException("Product "+product.getProductName()+" doesn't exist");
        }
        double cartPrice=cart.getTotalPrice()- (cartItem.getProductPrice()*cartItem.getQuantity());
   cartItem.setProductPrice(product.getSpecialPrice());
   cart.setTotalPrice(cartPrice + (cartItem.getProductPrice()*cartItem.getQuantity()));
cartItem=cartItemRepo.save(cartItem);


    }

    private Cart createCart() {
        Cart userCart=cartRepo.findCartByEmail((authUtile.loggedInEmail()));
        if (userCart!=null) {
            return userCart;
        }
        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setUser(authUtile.loggedInUser());
        Cart newCart=cartRepo.save(cart);
        return newCart;
    }
}
