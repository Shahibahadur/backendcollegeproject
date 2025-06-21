package com.ecommerce.controller;

import com.ecommerce.service.EsewaService;
import com.ecommerce.dto.EsewaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    private EsewaService esewaService;

    @PostMapping("/esewa/initiate")
    public ResponseEntity<Map<String, String>> initiateEsewaPayment(@RequestBody EsewaRequest esewaRequest) {
        try {
            Map<String, String> paymentDetails = esewaService.initiatePayment(esewaRequest);
            return ResponseEntity.ok(paymentDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/esewa/verify")
    public ResponseEntity<Map<String, String>> verifyEsewaPayment(@RequestParam("uuid") String uuid, @RequestParam("amount") String amount) {
        try {
            Map<String, String> verificationDetails = esewaService.verifyPayment(uuid, amount);
            return ResponseEntity.ok(verificationDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
} 