package com.example.kubernetes_demo.user_service.api;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kubernetes_demo.user_service.api.dto.CreateUserRequest;
import com.example.kubernetes_demo.user_service.api.dto.CreateUserResponse;
import com.example.kubernetes_demo.user_service.api.dto.UserResponse;
import com.example.kubernetes_demo.user_service.domain.UserService;
import com.example.kubernetes_demo.user_service.domain.exception.UserAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    
    private final UserService userService;
    private final UserApiResponseMapper userApiResponseMapper;

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse handleUserAlreadyExists(UserAlreadyExistsException e) {
        return ErrorResponse.builder(e, HttpStatus.CONFLICT, "User already exists").build();
    }

    @PostMapping("/api/users")
    public CreateUserResponse createUser(@RequestBody @Validated CreateUserRequest request) {
        var user = userService.create(request.username(), request.password());

        return userApiResponseMapper.toCreateUserResponse(user);
    }

    @GetMapping("/api/users/me")
    public UserResponse getUser(Principal principal) {
        var username = principal.getName();

        return new UserResponse(username);
    }
}
