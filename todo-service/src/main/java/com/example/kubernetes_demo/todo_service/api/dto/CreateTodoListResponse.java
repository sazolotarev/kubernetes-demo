package com.example.kubernetes_demo.todo_service.api.dto;

import java.time.Instant;

public record CreateTodoListResponse(String id, Instant createdAt, String name) {
}
