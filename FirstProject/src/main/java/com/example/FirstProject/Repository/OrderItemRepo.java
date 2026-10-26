package com.example.FirstProject.Repository;

import com.example.FirstProject.model.OrderItems;
import com.example.FirstProject.payload.OrderItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItems,Long> {
}
