package com.example.kubernetes_demo.user_service.domain.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final String idOrUsername;

    public UserNotFoundException(String message, String idOrUsername) {
        super(message);
        this.idOrUsername = idOrUsername;
    }
}
