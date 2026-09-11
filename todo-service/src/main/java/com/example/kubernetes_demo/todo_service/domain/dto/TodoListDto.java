package com.example.kubernetes_demo.todo_service.domain.dto;

import java.time.Instant;

public record TodoListDto(String id, Instant createdAt, String name, String userId) {
}
