package com.example.FirstProject.Service;

import com.example.FirstProject.Repository.CartRepo;
import com.example.FirstProject.Repository.CategoryRepo;
import com.example.FirstProject.Repository.ProductRepo;
import com.example.FirstProject.exception.APIException;
import com.example.FirstProject.exception.ResourceNotFoundException;
import com.example.FirstProject.model.Cart;
import com.example.FirstProject.model.Category;
import com.example.FirstProject.model.Product;
import com.example.FirstProject.payload.CartDTO;
import com.example.FirstProject.payload.ProductDTO;
import com.example.FirstProject.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    CartRepo  cartRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FileService fileService;
    @Autowired
    CartService cartService;
    @Value("${project.image}")
    String path;
    @Override
    public ProductDTO addProduct(Product products, Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException
                        ("Category ","categoryId",categoryId));
        boolean ifProductNotExists = true;
        List<Product> prouducts=category.getProuducts();
     for(Product p:prouducts){
         if (p.getProductName().equals(products.getProductName())) {
             ifProductNotExists = false;
             break;
         }
     }

if(ifProductNotExists) {
    products.setImage("def.png");
    products.setCategory(category);
    double specialPrice = (products.getPrice()) -
            (products.getDiscount() * 0.01) * products.getPrice();
    products.setSpecialPrice(specialPrice);
    Product savedProduct = productRepo.save(products);


    return modelMapper.map(savedProduct, ProductDTO.class);
}else {
    throw new APIException("Product Not Exists");
}
    }

    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable  pageableDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProduct= productRepo.findAll(pageableDetails);
        List<Product> savedProduct = pageProduct.getContent();
        List<ProductDTO> productDTO =  savedProduct.stream().
                map(product->modelMapper.map(product,ProductDTO.class)).toList();
      if (savedProduct.isEmpty()) {
          throw new APIException("No Product Exists");
      }

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pageProduct.getNumber());
        productResponse.setPageSize(pageProduct.getSize());
        productResponse.setTotalElements(pageProduct.getTotalElements());
        productResponse.setTotalPages(productResponse.getTotalPages());
        productResponse.setLastPage(pageProduct.isLast());




        return productResponse;
    }

    @Override
    public ProductResponse findProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category ","categoryId",categoryId));
  Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc") ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable  pageableDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProduct= productRepo.findByCategoryOrderByPriceAsc(category,pageableDetails);
        List<Product> savedProduct = pageProduct.getContent();

        List<ProductDTO> productDTO=savedProduct.stream().
                map(product->modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pageProduct.getNumber());
        productResponse.setPageSize(pageProduct.getSize());
        productResponse.setTotalElements(pageProduct.getTotalElements());
        productResponse.setTotalPages(productResponse.getTotalPages());
        productResponse.setLastPage(pageProduct.isLast());
        return productResponse;

    }

    @Override
    public ProductResponse findProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc") ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable  pageableDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProduct= productRepo.findByProductNameLikeIgnoreCase('%'+keyword+'%',pageableDetails);
        List<Product> savedProduct = pageProduct.getContent();
        List<ProductDTO> productDTOS=savedProduct.stream().map(product->modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProduct.getNumber());
        productResponse.setPageSize(pageProduct.getSize());
        productResponse.setTotalElements(pageProduct.getTotalElements());
        productResponse.setTotalPages(productResponse.getTotalPages());
        productResponse.setLastPage(pageProduct.isLast());
        return productResponse;


    }

    @Override
    public ProductDTO updateProduct(Long productId, Product product) {
        Product productFromDB =productRepo.findById(productId).orElseThrow(()
                -> new ResourceNotFoundException("Product","PoductId",productId));
        productFromDB.setPrice(product.getPrice());
        productFromDB.setDiscount(product.getDiscount());
        productFromDB.setProductName(product.getProductName());
        productFromDB.setDiscount(product.getDiscount());
        productFromDB.setQuantity(product.getQuantity());
        productFromDB.setSpecialPrice(product.getSpecialPrice());



Product product1 =productRepo.save(productFromDB);
List<Cart> carts=cartRepo.findCartByProductId(productId);
List<CartDTO> cartDTOS=carts.stream().map(cart->
                {
         CartDTO cartDTO=       modelMapper.map(cart,CartDTO.class);
         List<ProductDTO >productDTO=  cart.getItems().stream().map(
                 p->modelMapper.map(p.getProduct(),ProductDTO.class))
                 .collect(Collectors.toList());
         cartDTO.setProducts(productDTO);
return cartDTO;
                }).toList();

cartDTOS.forEach(cart->{
    cartService.updateProductInCart(cart.getCartId(),productId);

});

        return modelMapper.map(product1,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product =productRepo.findById(productId).orElseThrow(()
                -> new ResourceNotFoundException("Product","PoductId",productId));
        List<Cart> carts=cartRepo.findCartByProductId(productId);
        carts.forEach(cart->{
            cartService.deleteProductFromCart(cart.getCartId(),productId);
        });
        productRepo.delete(product);
        return modelMapper.map(product,ProductDTO.class);

    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product prouductFromDB=productRepo.findById(productId).orElseThrow(()
                -> new ResourceNotFoundException("Product","PoductId",productId));

        String fileName=fileService.uploadImage(path,image);
prouductFromDB.setImage(fileName);
Product productSaved =productRepo.save(prouductFromDB);

       return modelMapper.map(productSaved,ProductDTO.class);
    }


}
