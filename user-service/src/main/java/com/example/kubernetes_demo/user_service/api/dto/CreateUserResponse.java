package com.example.kubernetes_demo.user_service.api.dto;

import java.time.Instant;

public record CreateUserResponse(String id, Instant createdAt, String username) {
}
