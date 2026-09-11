package com.example.kubernetes_demo.user_service.data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("users")
@Getter
@Setter
public class User {

    @Id
    private UUID id;

    private Instant createdAt = Instant.now();

    private String username;

    private String password;

    private Set<Role> roles;
}
