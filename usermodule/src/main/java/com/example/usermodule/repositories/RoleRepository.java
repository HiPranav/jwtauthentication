package com.example.usermodule.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.usermodule.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
}