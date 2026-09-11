package com.example.kubernetes_demo.todo_service.domain.exception;

import lombok.Getter;

@Getter
public class TodoListNotFoundException extends RuntimeException {

    private final String id;

    public TodoListNotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }
}
