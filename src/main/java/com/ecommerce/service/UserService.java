package com.ecommerce.service;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.dto.request.LoginRequest;
import com.ecommerce.model.User;

public interface UserService {
    /**
     * Registers a user and handles profile picture upload if provided.
     * @return the registered user
     */
    User registerUser(RegisterRequest registerRequest);
    User loginUser(LoginRequest loginRequest);
    User findUserById(Long userId);
} 