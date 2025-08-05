package com.example.usermodule.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.usermodule.repositories.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepo;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.usermodule.entity.User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()))
        );
    }

}