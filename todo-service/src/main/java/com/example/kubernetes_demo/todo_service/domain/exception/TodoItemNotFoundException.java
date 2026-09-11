package com.example.kubernetes_demo.todo_service.domain.exception;

import lombok.Getter;

@Getter
public class TodoItemNotFoundException extends RuntimeException {
    
    private final String id;

    public TodoItemNotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }
}
