package com.example.kubernetes_demo.user_service.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank
        @Size(min = 2)
        String username,
        @NotBlank
        @Size(min = 6)
        String password) {
}
