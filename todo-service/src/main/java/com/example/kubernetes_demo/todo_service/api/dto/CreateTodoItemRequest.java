package com.example.kubernetes_demo.todo_service.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoItemRequest(
    @NotBlank
    String todoListId,
    @NotBlank
    String description) {
}
