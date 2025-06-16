package com.ecommerce.service;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.dto.request.LoginRequest;

public interface UserService {
    void registerUser(RegisterRequest registerRequest);
    User loginUser(LoginRequest loginRequest);
} 