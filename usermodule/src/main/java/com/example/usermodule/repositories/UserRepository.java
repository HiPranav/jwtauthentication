package com.example.usermodule.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.usermodule.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}