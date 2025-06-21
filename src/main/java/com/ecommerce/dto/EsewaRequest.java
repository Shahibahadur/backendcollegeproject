package com.ecommerce.dto;

import java.math.BigDecimal;

public class EsewaRequest {
    private BigDecimal amount;
    // In a real app, you'd likely have a list of cart items here
    // For now, we'll just use the total amount from the frontend.

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
} 