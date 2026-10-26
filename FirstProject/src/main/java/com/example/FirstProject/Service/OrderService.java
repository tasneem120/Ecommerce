package com.example.FirstProject.Service;

import com.example.FirstProject.payload.OrderDTO;
import jakarta.transaction.Transactional;

public interface OrderService {
    @Transactional

    OrderDTO placeOrder(String emailId, String paymentMethod, Long addressId, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
