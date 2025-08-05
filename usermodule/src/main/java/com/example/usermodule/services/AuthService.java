package com.example.usermodule.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.example.usermodule.dtos.*;
import com.example.usermodule.entity.*;
import com.example.usermodule.repositories.*;

@Service
public class AuthService {
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;

    public User registerUser(RegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Role role = roleRepo.findById(request.getRoleId())
            .orElseThrow(() -> new RuntimeException("Invalid Role"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setRole(role);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return userRepo.save(user);
    }
}
