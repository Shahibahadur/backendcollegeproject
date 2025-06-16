package com.ecommerce.service.impl;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.dto.request.LoginRequest;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        // Check if user exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email " + registerRequest.getEmail() + " already registered");
        }

        // Create and save new user
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .build();

        userRepository.save(user);
    }

    @Override
    public User loginUser(LoginRequest loginRequest) {
        return userRepository.findByEmail(loginRequest.getEmail())
            .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            .orElse(null);
    }
} 