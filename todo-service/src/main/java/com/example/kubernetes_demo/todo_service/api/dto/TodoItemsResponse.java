package com.example.kubernetes_demo.todo_service.api.dto;

import java.util.List;

public record TodoItemsResponse(List<Entry> todoItems) {

    public record Entry(String id, String description) { 
    }
}
