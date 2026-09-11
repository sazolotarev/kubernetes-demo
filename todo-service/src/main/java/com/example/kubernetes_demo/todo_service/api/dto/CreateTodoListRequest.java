package com.example.kubernetes_demo.todo_service.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoListRequest(
        @NotBlank
        String name) {
}
