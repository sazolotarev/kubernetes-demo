package com.example.kubernetes_demo.user_service.domain.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private final String username;

    public UserAlreadyExistsException(String message, String username) {
        super(message);
        this.username = username;
    }
}
