package com.ecommerce.controller;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.dto.request.LoginRequest;
import com.ecommerce.service.UserService;
import com.ecommerce.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import com.ecommerce.model.User;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // JSON registration endpoint for frontend compatibility
    @PostMapping(value = "/register/json", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> registerUserJson(@Valid @RequestBody RegisterRequest registerRequest) {
        System.out.println("[DEBUG] Received JSON registration: " +
            " name=" + registerRequest.getName() +
            ", email=" + registerRequest.getEmail() +
            ", phone=" + registerRequest.getPhone() +
            ", address=" + registerRequest.getAddress()
        );
        
        Map<String, Object> response = new HashMap<>();
        try {
            User registeredUser = userService.registerUser(registerRequest);
            String token = jwtUtil.generateToken(registeredUser.getEmail(), registeredUser.getId());
            
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("data", registeredUser);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Multipart registration endpoint for file uploads
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> registerUserMultipart(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestPart(value = "profilePicture", required = false) org.springframework.web.multipart.MultipartFile profilePicture,
            jakarta.servlet.http.HttpServletRequest request) {
        // Print all request parameters
        System.out.println("[DEBUG] All request parameters:");
        request.getParameterMap().forEach((k, v) -> System.out.println("  " + k + ": " + java.util.Arrays.toString(v)));

        System.out.println("[DEBUG] Received multipart registration: " +
            " name=" + name +
            ", email=" + email +
            ", password=" + password +
            ", phone=" + phone +
            ", address=" + address +
            ", profilePicture=" + (profilePicture != null ? profilePicture.getOriginalFilename() : null)
        );
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(name);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setPhone(phone);
        registerRequest.setAddress(address);
        registerRequest.setProfilePicture(profilePicture);
        Map<String, Object> response = new HashMap<>();
        try {
            User registeredUser = userService.registerUser(registerRequest);
            String token = jwtUtil.generateToken(registeredUser.getEmail(), registeredUser.getId());
            
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("data", registeredUser);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Serve profile pictures
    @GetMapping("/profile-picture/{filename}")
    public ResponseEntity<?> getProfilePicture(@PathVariable String filename) {
        java.nio.file.Path imagePath = java.nio.file.Paths.get("uploads/images/profile-pictures/", filename);
        if (!java.nio.file.Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }
        try {
            org.springframework.core.io.Resource fileResource = new org.springframework.core.io.UrlResource(imagePath.toUri());
            return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(fileResource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error loading profile picture");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.loginUser(loginRequest);
        Map<String, Object> response = new HashMap<>();
        
        if (user != null) {
            String token = jwtUtil.generateToken(user.getEmail(), user.getId());
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("data", user);
            response.put("token", token);
        } else {
            response.put("success", false);
            response.put("message", "Invalid email or password");
            response.put("data", null);
        }
        
        return ResponseEntity.ok(response);
    }

    // Token verification endpoint
    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extractEmail(token);
                Long userId = jwtUtil.extractUserId(token);
                
                if (jwtUtil.validateToken(token, email)) {
                    User user = userService.findUserById(userId);
                    if (user != null) {
                        response.put("success", true);
                        response.put("message", "Token is valid");
                        response.put("data", user);
                        return ResponseEntity.ok(response);
                    }
                }
            }
            
            response.put("success", false);
            response.put("message", "Invalid token");
            return ResponseEntity.status(401).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Token verification failed");
            return ResponseEntity.status(401).body(response);
        }
    }
} 