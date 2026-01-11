package com.example.oms.dto;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(
        @NotNull Long userId
) {}
