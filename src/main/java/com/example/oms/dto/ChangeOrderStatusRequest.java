package com.example.oms.dto;

import com.example.oms.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeOrderStatusRequest(
        @NotNull OrderStatus status
) {}
