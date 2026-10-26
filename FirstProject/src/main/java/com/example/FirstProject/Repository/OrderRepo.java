package com.example.FirstProject.Repository;

import com.example.FirstProject.model.Order;
import com.example.FirstProject.payload.PaymentDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
