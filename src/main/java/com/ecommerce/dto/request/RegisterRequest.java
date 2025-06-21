package com.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    public org.springframework.web.multipart.MultipartFile profilePicture;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    public String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    public String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    public String password;
    
    @NotBlank(message = "Phone is required")
    public String phone;
    
    @NotBlank(message = "Address is required")
    public String address;
} 