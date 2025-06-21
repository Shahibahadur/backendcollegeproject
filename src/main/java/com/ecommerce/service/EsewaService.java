package com.ecommerce.service;

import com.ecommerce.dto.EsewaRequest;
import java.util.Map;

public interface EsewaService {
    Map<String, String> initiatePayment(EsewaRequest request) throws Exception;
    Map<String, String> verifyPayment(String transactionUuid, String totalAmount) throws Exception;
} 