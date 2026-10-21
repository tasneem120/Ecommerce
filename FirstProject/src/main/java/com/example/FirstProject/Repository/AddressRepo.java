package com.example.FirstProject.Repository;

import com.example.FirstProject.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,Long> {
}
