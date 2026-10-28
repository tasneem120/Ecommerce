package com.example.FirstProject.Controller;

import com.example.FirstProject.Service.OrderService;
import com.example.FirstProject.payload.OrderDTO;
import com.example.FirstProject.payload.OrderRequestDTO;
import com.example.FirstProject.util.AuthUtile;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private AuthUtile  authUtile;
    @Autowired
    private OrderService orderService;
    @Tag(name="Order API",description = "APIS for managing order")

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(
            @Parameter(description = "Payment Method to order")
            @PathVariable String paymentMethod,
                                                  @RequestBody OrderRequestDTO orderRequestDTO) {
String emailId=authUtile.loggedInEmail();
   OrderDTO orderDTO= orderService.placeOrder(
        emailId,
        paymentMethod,
        orderRequestDTO.getAddressId(),
        orderRequestDTO.getPgName(),
        orderRequestDTO.getPgPaymentId(),
        orderRequestDTO.getPgStatus(),
        orderRequestDTO.getPgResponseMessage()
);
   return new ResponseEntity<>(orderDTO,HttpStatus.CREATED);

    }
}
