package com.example.oms.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        @Positive BigDecimal price
) {}
