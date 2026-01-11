package com.example.oms.dto;

import com.example.oms.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        Long userId,
        OrderStatus status,
        Instant createdAt,
        BigDecimal total,
        List<OrderItemResponse> items
) {}
