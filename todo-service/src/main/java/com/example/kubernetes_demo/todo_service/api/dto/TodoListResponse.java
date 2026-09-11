package com.example.kubernetes_demo.todo_service.api.dto;

import java.time.Instant;

public record TodoListResponse(String id, Instant createdAt, String name) {
}
