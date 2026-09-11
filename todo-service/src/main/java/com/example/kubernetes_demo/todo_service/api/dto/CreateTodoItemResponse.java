package com.example.kubernetes_demo.todo_service.api.dto;

import java.time.Instant;

public record CreateTodoItemResponse(String id, Instant createdAt, String todoListId, String description) {
}
