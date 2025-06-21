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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(RegisterRequest registerRequest) {
        // Check if user exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email " + registerRequest.getEmail() + " already registered");
        }

        String profilePictureName = null;
        System.out.println("[DEBUG] Profile picture check: " + (registerRequest.getProfilePicture() != null ? "File present" : "No file"));
        
        if (registerRequest.getProfilePicture() != null && !registerRequest.getProfilePicture().isEmpty()) {
            try {
                String uploadDir = "uploads/images/profile-pictures/";
                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
                java.nio.file.Files.createDirectories(uploadPath);
                
                String originalFilename = registerRequest.getProfilePicture().getOriginalFilename();
                System.out.println("[DEBUG] Original filename: " + originalFilename);
                
                String fileExtension = "";
                if (originalFilename != null && originalFilename.lastIndexOf('.') > 0) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                } else {
                    fileExtension = ".jpg"; // Default extension if missing
                }
                
                // Generate unique filename using timestamp
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
                String timestamp = now.format(formatter);
                String randomSuffix = String.valueOf((int)(Math.random() * 1000));
                String newFileName = "profile_" + timestamp + "_" + randomSuffix + fileExtension;
                
                java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDir, newFileName);
                
                System.out.println("[DEBUG] Saving file to: " + filePath.toAbsolutePath());
                registerRequest.getProfilePicture().transferTo(filePath);
                
                // Store only the image name in database
                profilePictureName = newFileName;
                System.out.println("[DEBUG] Profile picture name saved: " + profilePictureName);
                
                // Verify file was saved
                if (java.nio.file.Files.exists(filePath)) {
                    System.out.println("[DEBUG] File successfully saved, size: " + java.nio.file.Files.size(filePath) + " bytes");
                } else {
                    System.out.println("[DEBUG] ERROR: File was not saved!");
                }
            } catch (Exception e) {
                System.out.println("[DEBUG] Error uploading profile picture: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to upload profile picture", e);
            }
        } else {
            System.out.println("[DEBUG] No profile picture provided or file is empty");
        }

        // Validate password
        if (registerRequest.getPassword() == null || registerRequest.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        // Create and save new user
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .profilePicture(profilePictureName)
                .build();

        User savedUser = userRepository.save(user);
        System.out.println("[DEBUG] User saved with ID: " + savedUser.getId() + ", Profile picture: " + savedUser.getProfilePicture());
        
        return savedUser;
    }

    @Override
    public User loginUser(LoginRequest loginRequest) {
        return userRepository.findByEmail(loginRequest.getEmail())
            .filter(user -> passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            .orElse(null);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
} 