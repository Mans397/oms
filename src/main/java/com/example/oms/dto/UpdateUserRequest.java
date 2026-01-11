package com.example.oms.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserRequest(
        String name,
        @Email String email
) {}
