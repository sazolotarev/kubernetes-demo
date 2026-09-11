package com.example.kubernetes_demo.todo_service.domain.dto;

import java.time.Instant;
import java.util.UUID;

public record TodoItemDto(UUID id, Instant createdAt, UUID todoListId, String description) {
}
