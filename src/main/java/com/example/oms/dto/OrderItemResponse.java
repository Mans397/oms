package com.example.oms.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String productName,
        int quantity,
        BigDecimal priceAtOrderTime,
        BigDecimal lineTotal
) {}
