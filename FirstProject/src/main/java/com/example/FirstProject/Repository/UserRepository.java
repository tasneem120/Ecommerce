package com.example.FirstProject.Repository;

import com.example.FirstProject.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    boolean existsByUserEmail(@NotBlank @Size(min = 3, max = 20) String username);
    boolean existsByUserName(@NotBlank @Size(min = 3, max = 20) String username);
}
