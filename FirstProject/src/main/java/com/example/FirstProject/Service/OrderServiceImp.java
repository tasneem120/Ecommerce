package com.example.FirstProject.Service;

import com.example.FirstProject.Repository.*;
import com.example.FirstProject.exception.APIException;
import com.example.FirstProject.exception.ResourceNotFoundException;
import com.example.FirstProject.model.*;
import com.example.FirstProject.payload.OrderDTO;
import com.example.FirstProject.payload.OrderItemDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    CartRepo cartRepo;
 @Autowired
 AddressRepo addressRepo;
 @Autowired
 PaymentRepo paymentRepo;
 @Autowired
 OrderItemRepo orderItemRepo;
 @Autowired
 ProductRepo productRepo;
 @Autowired
 CartService cartService;
 @Autowired
 ModelMapper modelMapper;
    @Override
    @Transactional

    public OrderDTO placeOrder(String emailId, String paymentMethod,
                               Long addressId, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage) {
        Cart cart=cartRepo.findCartByEmail(emailId);
        if(cart==null){
            throw new ResourceNotFoundException("Cart","emailId",emailId);
        }
        Address address=addressRepo.findById(addressId).orElseThrow(()->new ResourceNotFoundException("Address","id",addressId));
Order order=new Order();
order.setOrderDate(LocalDate.now());
order.setOrderTotal(cart.getTotalPrice());
order.setOrderStatus("Accepted order!");
order.setEmail(emailId);
order.setAddresses(address);
        Payment  payment=new Payment(paymentMethod,pgPaymentId,pgStatus,pgResponseMessage,pgName);
payment.setOrder(order);
payment=paymentRepo.save(payment);
 order.setPayment(payment);
 Order savedOrder=orderRepo.save(order);
 List<CartItem> cartItem= cart.getItems();
 if (cartItem.isEmpty()){
     throw new APIException("cartItem is empty");
 }
        List<OrderItems> orderItems=new ArrayList<>();

 for (CartItem cartItem1:cartItem){
     OrderItems orderItem=new OrderItems();
     orderItem.setProduct(cartItem1.getProduct());
     orderItem.setQuantity(cartItem1.getQuantity());
     orderItem.setDiscount(cartItem1.getDiscount());
     orderItem.setOrderProductPrice(cartItem1.getProductPrice());
     orderItem.setOrder(savedOrder);
     orderItems.add(orderItem);
 }
orderItems=orderItemRepo.saveAll(orderItems);
 cart.getItems().forEach(item->{
     int quantity=item.getQuantity();
     Product product=item.getProduct();
  product.setQuantity( product.getQuantity()-quantity);
  productRepo.save(product);
  cartService.deleteProductFromCart(cart.getCartId(),  item.getProduct().getProductId());
 });
 OrderDTO orderDTO=modelMapper.map(savedOrder,OrderDTO.class);
 orderItems.forEach(item->orderDTO.getOrderItems()
         .add(modelMapper.map(item, OrderItemDTO.class)));
orderDTO.setAddressId(addressId);

        return orderDTO;
    }
}
