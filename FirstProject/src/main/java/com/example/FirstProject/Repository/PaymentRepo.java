package com.example.FirstProject.Repository;

import com.example.FirstProject.model.Payment;
import com.example.FirstProject.payload.PaymentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
