package com.example.kubernetes_demo.todo_service.api.dto;

import java.util.List;

public record TodoListsResponse(List<Entry> todoLists) {

    public record Entry(String id, String name) { 
    }
}
