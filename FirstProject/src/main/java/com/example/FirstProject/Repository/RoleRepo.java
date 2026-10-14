package com.example.FirstProject.Repository;

import com.example.FirstProject.model.AppRoles;
import com.example.FirstProject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRoles appRoles);
}
