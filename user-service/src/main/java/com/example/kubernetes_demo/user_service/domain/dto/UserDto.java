package com.example.kubernetes_demo.user_service.domain.dto;

import java.time.Instant;
import java.util.UUID;

public record UserDto(UUID id, Instant createdAt, String username) {
}
