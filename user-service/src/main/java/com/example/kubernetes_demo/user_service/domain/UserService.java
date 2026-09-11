package com.example.kubernetes_demo.user_service.domain;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.kubernetes_demo.user_service.data.Role;
import com.example.kubernetes_demo.user_service.data.User;
import com.example.kubernetes_demo.user_service.data.UserRepository;
import com.example.kubernetes_demo.user_service.domain.dto.UserDto;
import com.example.kubernetes_demo.user_service.domain.exception.UserAlreadyExistsException;
import com.example.kubernetes_demo.user_service.domain.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto create(String username, String password) {
        var user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            log.error("User already exists: {}", username);
            throw new UserAlreadyExistsException("User already exists", username);
        }

        user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        log.info("Created user {} {}", user.getId(), user.getUsername());

        return userMapper.toDto(user);
    }

    public UserDto getByUsername(String username) {
        var userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            log.info("User not found: {}", username);
            throw new UserNotFoundException("User not found: " + username, username);
        }

        return userMapper.toDto(userOptional.get());
    }
}
