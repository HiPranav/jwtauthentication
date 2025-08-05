package com.example.usermodule.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.usermodule.dtos.*;
import com.example.usermodule.entity.User;
import com.example.usermodule.security.*;
import com.example.usermodule.services.*;


@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired private AuthenticationManager authManager;
    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = authService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token, userDetails.getAuthorities().toString()));
    }
}