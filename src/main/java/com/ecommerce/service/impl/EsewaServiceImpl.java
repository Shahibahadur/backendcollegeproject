package com.ecommerce.service.impl;

import com.ecommerce.dto.EsewaRequest;
import com.ecommerce.service.EsewaService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class EsewaServiceImpl implements EsewaService {

    private static final String ESEWA_SECRET_KEY = "8gBm/:&EnhH.1/q(";
    private static final String ESEWA_PRODUCT_CODE = "EPAYTEST";
    private static final String ESEWA_SUCCESS_URL = "http://localhost:3000/esewa-success";
    private static final String ESEWA_FAILURE_URL = "http://localhost:3000/esewa-failure";
    private static final String ESEWA_VERIFY_URL = "https://rc.esewa.com.np/api/epay/transaction/status/";

    @Override
    public Map<String, String> initiatePayment(EsewaRequest request) throws Exception {
        String transactionUuid = UUID.randomUUID().toString();
        
        BigDecimal totalAmount = request.getAmount().setScale(2, RoundingMode.HALF_UP);
        
        // For testing, other charges are zero. The 'amount' is the total minus these charges.
        BigDecimal taxAmount = new BigDecimal("0.00");
        BigDecimal serviceCharge = new BigDecimal("0.00");
        BigDecimal deliveryCharge = new BigDecimal("0.00");
        BigDecimal amount = totalAmount.subtract(taxAmount).subtract(serviceCharge).subtract(deliveryCharge);
        
        // The value for total_amount in the signature must EXACTLY match the value in the form field.
        // eSewa's examples often use integers for whole numbers (e.g., "100" instead of "100.00").
        String totalAmountForFormAndSignature = totalAmount.stripTrailingZeros().scale() <= 0
            ? String.valueOf(totalAmount.intValue())
            : totalAmount.toPlainString();

        String signatureMessage = String.format("total_amount=%s,transaction_uuid=%s,product_code=%s",
                totalAmountForFormAndSignature, transactionUuid, ESEWA_PRODUCT_CODE);

        String signature = generateHmacSha256(signatureMessage, ESEWA_SECRET_KEY);

        Map<String, String> paymentDetails = new HashMap<>();
        paymentDetails.put("amount", amount.toPlainString());
        paymentDetails.put("tax_amount", taxAmount.toPlainString());
        paymentDetails.put("total_amount", totalAmountForFormAndSignature); // Use the same formatted string for the form
        paymentDetails.put("transaction_uuid", transactionUuid);
        paymentDetails.put("product_code", ESEWA_PRODUCT_CODE);
        paymentDetails.put("product_service_charge", serviceCharge.toPlainString());
        paymentDetails.put("product_delivery_charge", deliveryCharge.toPlainString());
        paymentDetails.put("success_url", ESEWA_SUCCESS_URL);
        paymentDetails.put("failure_url", ESEWA_FAILURE_URL);
        paymentDetails.put("signed_field_names", "total_amount,transaction_uuid,product_code");
        paymentDetails.put("signature", signature);

        return paymentDetails;
    }

    @Override
    public Map<String, String> verifyPayment(String transactionUuid, String totalAmount) throws Exception {
        String url = String.format("%s?product_code=%s&total_amount=%s&transaction_uuid=%s",
                ESEWA_VERIFY_URL, ESEWA_PRODUCT_CODE, totalAmount, transactionUuid);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, String>>(){});

        // Here you would typically update your order status in the database
        // For now, we'll just return the response from eSewa
        return responseMap;
    }

    private String generateHmacSha256(String message, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
} 